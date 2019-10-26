package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.exception.SpecializationExistsException;
import pl.com.tutti.tuttiserver.rest.exception.SpecializationNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.data.SpecializationData;
import pl.com.tutti.tuttiserver.service.SpecializationsService;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SpecializationRestController {

    private final SpecializationsService specializationsService;
    private final UsersService usersService;

    @GetMapping("/specializations")
    ResponseEntity getSpecializations(Principal principal){

        Users user = usersService.findByUsername(principal.getName());
        user = usersService.getWithSpecializations(user);

        List<Specialization> specializations = user.getSpecializations();
        if(specializations == null)
            specializations = new ArrayList();
        else
            specializations.stream().forEach(spec -> spec.setUsername(null));

        return ResponseFactory.createPayloadResponse(specializations, "getSpecializations");
    }

    @PostMapping("/specializations")
    public ResponseEntity addSpecialization(@Valid @RequestBody SpecializationData specializationData, Principal principal){
        if(specializationData.getId() != null)
            throw new SpecializationExistsException("To update existing use PATCH!");

        return getResponseEntity(specializationData, principal);
    }

    @PatchMapping("/specializations")
    public ResponseEntity updateSpecialization(@Valid @RequestBody SpecializationData specializationData, Principal principal) {
        if(specializationData.getId() == null)
            throw new SpecializationNotExistsException("To save new use POST!");

        Specialization specialization = specializationsService.findById(specializationData.getId());
        if(!specialization.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        specialization.setDislikes(specializationData.getDislikes());
        specialization.setLikes(specializationData.getLikes());
        specialization.setLevel(specializationData.getLevel());
        specialization.setName(specializationData.getName());
        specialization.setSalary(specializationData.getSalary());

        specializationsService.save(specialization);

        return ResponseFactory.createBasicResponse("updateSpecialization");
    }

    @DeleteMapping("/specializations/{id}")
    @ResponseBody
    public ResponseEntity deleteSpecialization(@PathVariable("id") Integer id, Principal principal) {
        Specialization specialization = specializationsService.findById(id);
        if(!specialization.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        specializationsService.delete(specialization);

        return ResponseFactory.createBasicResponse("deleteSpecialization");
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid SpecializationData specializationData, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Specialization specialization = new Specialization();
        specialization.setUsername(user);
        specialization.setDislikes(specializationData.getDislikes());
        specialization.setLikes(specializationData.getLikes());
        specialization.setLevel(specializationData.getLevel());
        specialization.setName(specializationData.getName());
        specialization.setSalary(specializationData.getSalary());
        specialization.setId(specializationData.getId());

        user = usersService.getWithSpecializations(user);
        if(user.getSpecializations() == null)
            user.setSpecializations(new ArrayList<>());
        user.addSpecialization(specialization);

        specializationsService.save(specialization);

        return ResponseFactory.createPayloadResponse(specialization.getId(), "addSpecialization");
    }
}
