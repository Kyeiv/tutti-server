package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.exception.SpecializationExistsException;
import pl.com.tutti.tuttiserver.rest.exception.SpecializationNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.request.SpecializationRequest;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;
import pl.com.tutti.tuttiserver.rest.response.SpecializationResponse;
import pl.com.tutti.tuttiserver.rest.response.SpecializationsResponse;
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

        SpecializationsResponse specializationsResponse = new SpecializationsResponse();
        specializationsResponse.setMessage("getSpecializations");
        specializationsResponse.setStatus(HttpStatus.OK.value());
        specializationsResponse.setTimeStamp(System.currentTimeMillis());
        specializationsResponse.setSpecializations(specializations);

        return new ResponseEntity<>(specializationsResponse, HttpStatus.OK);
    }

    @PostMapping("/specializations")
    public ResponseEntity addSpecialization(@Valid @RequestBody SpecializationRequest specializationRequest, Principal principal){
        if(specializationRequest.getId() != null)
            throw new SpecializationExistsException("To update existing use PATCH!");

        return getResponseEntity(specializationRequest, principal);
    }

    @PatchMapping("/specializations")
    public ResponseEntity updateSpecialization(@Valid @RequestBody SpecializationRequest specializationRequest, Principal principal) {
        if(specializationRequest.getId() == null)
            throw new SpecializationNotExistsException("To save new use POST!");

        Specialization specialization = specializationsService.findById(specializationRequest.getId());
        if(!specialization.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        specialization.setDislikes(specializationRequest.getDislikes());
        specialization.setLikes(specializationRequest.getLikes());
        specialization.setLevel(specializationRequest.getLevel());
        specialization.setName(specializationRequest.getName());
        specialization.setSalary(specializationRequest.getSalary());

        specializationsService.save(specialization);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("updateSpecialization");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/specializations/{id}")
    @ResponseBody
    public ResponseEntity deleteSpecialization(@PathVariable("id") Integer id, Principal principal) {
        Specialization specialization = specializationsService.findById(id);
        if(!specialization.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        specializationsService.delete(specialization);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("deleteSpecialization");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid SpecializationRequest specializationRequest, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Specialization specialization = new Specialization();
        specialization.setUsername(user);
        specialization.setDislikes(specializationRequest.getDislikes());
        specialization.setLikes(specializationRequest.getLikes());
        specialization.setLevel(specializationRequest.getLevel());
        specialization.setName(specializationRequest.getName());
        specialization.setSalary(specializationRequest.getSalary());
        specialization.setId(specializationRequest.getId());

        user = usersService.getWithSpecializations(user);
        if(user.getSpecializations() == null)
            user.setSpecializations(new ArrayList<>());
        user.addSpecialization(specialization);

        specializationsService.save(specialization);

        SpecializationResponse specializationResponse = new SpecializationResponse();
        specializationResponse.setMessage("addSpecialization");
        specializationResponse.setStatus(HttpStatus.OK.value());
        specializationResponse.setTimeStamp(System.currentTimeMillis());
        specializationResponse.setElementId(specialization.getId());

        return new ResponseEntity<>(specializationResponse, HttpStatus.OK);
    }
}
