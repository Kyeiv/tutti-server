package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Availbility;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.exception.AvailbilityExistsException;
import pl.com.tutti.tuttiserver.rest.exception.AvailbilityNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.data.AvailbilityData;
import pl.com.tutti.tuttiserver.service.AvailbilityService;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AvailbilityRestController {
    private final AvailbilityService availbilityService;

    private final UsersService usersService;

    @GetMapping("/availbilities")
    ResponseEntity getAvailbilities(Principal principal){

        Users user = usersService.findByUsername(principal.getName());
        user = usersService.getWithSpecializations(user);

        List<Availbility> availbilities = user.getAvailbilities();
        if(availbilities == null)
            availbilities = new ArrayList();
        else
            availbilities.stream().forEach(spec -> spec.setUsername(null));

        return ResponseFactory.createPayloadResponse(availbilities, "getAvailbilities");
    }

    @PostMapping("/availbilities")
    public ResponseEntity addAvailbility(@Valid @RequestBody AvailbilityData availbilityData, Principal principal){
        if(availbilityData.getId() != null)
            throw new AvailbilityExistsException("To update existing use PATCH!");

        return getResponseEntity(availbilityData, principal);
    }

    @PatchMapping("/availbilities")
    public ResponseEntity updateAvailbility(@Valid @RequestBody AvailbilityData availbilityData, Principal principal) {
        if(availbilityData.getId() == null)
            throw new AvailbilityNotExistsException("To save new use POST!");

        Availbility availbility = availbilityService.findById(availbilityData.getId());
        if(!availbility.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        availbility.setDayOfTheWeek(availbilityData.getDayOfTheWeek());
        availbility.setHourBegin(availbilityData.getHourBegin());
        availbility.setHourEnd(availbilityData.getHourEnd());

        availbilityService.save(availbility);

        return ResponseFactory.createBasicResponse("updateAvailbility");
    }

    @DeleteMapping("/availbilities/{id}")
    @ResponseBody
    public ResponseEntity deleteAvailbility(@PathVariable("id") Integer id, Principal principal) {
        Availbility availbility = availbilityService.findById(id);
        if(!availbility.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        availbilityService.delete(availbility);

       return ResponseFactory.createBasicResponse("deleteAvailbility");
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid AvailbilityData availbilityData, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Availbility availbility = new Availbility();
        availbility.setUsername(user);
        availbility.setDayOfTheWeek(availbilityData.getDayOfTheWeek());
        availbility.setHourBegin(availbilityData.getHourBegin());
        availbility.setHourEnd(availbilityData.getHourEnd());

        user = usersService.getWithAvailbilities(user);
        if(user.getAvailbilities() == null)
            user.setAvailbilities(new ArrayList<>());
        user.addAvailbility(availbility);

        availbilityService.save(availbility);

        return ResponseFactory.createPayloadResponse(availbility.getId(), "addAvailbility");
    }
}
