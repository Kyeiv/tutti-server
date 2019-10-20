package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Availbility;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.exception.AvailbilityExistsException;
import pl.com.tutti.tuttiserver.rest.exception.AvailbilityNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.request.AvailbilityRequest;
import pl.com.tutti.tuttiserver.rest.response.AvailbilitiesResponse;
import pl.com.tutti.tuttiserver.rest.response.AvailbilityResponse;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;
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

        AvailbilitiesResponse availbilitiesResponse = new AvailbilitiesResponse();
        availbilitiesResponse.setMessage("getAvailbilities");
        availbilitiesResponse.setStatus(HttpStatus.OK.value());
        availbilitiesResponse.setTimeStamp(System.currentTimeMillis());
        availbilitiesResponse.setAvailbilities(availbilities);

        return new ResponseEntity<>(availbilitiesResponse, HttpStatus.OK);
    }

    @PostMapping("/availbilities")
    public ResponseEntity addAvailbility(@Valid @RequestBody AvailbilityRequest availbilityRequest, Principal principal){
        if(availbilityRequest.getId() != null)
            throw new AvailbilityExistsException("To update existing use PATCH!");

        return getResponseEntity(availbilityRequest, principal);
    }

    @PatchMapping("/availbilities")
    public ResponseEntity updateAvailbility(@Valid @RequestBody AvailbilityRequest availbilityRequest, Principal principal) {
        if(availbilityRequest.getId() == null)
            throw new AvailbilityNotExistsException("To save new use POST!");

        Availbility availbility = availbilityService.findById(availbilityRequest.getId());
        if(!availbility.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        availbility.setDayOfTheWeek(availbilityRequest.getDayOfTheWeek());
        availbility.setHourBegin(availbilityRequest.getHourBegin());
        availbility.setHourEnd(availbilityRequest.getHourEnd());

        availbilityService.save(availbility);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("updateAvailbility");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/availbilities/{id}")
    @ResponseBody
    public ResponseEntity deleteAvailbility(@PathVariable("id") Integer id, Principal principal) {
        Availbility availbility = availbilityService.findById(id);
        if(!availbility.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        availbilityService.delete(availbility);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("deleteAvailbility");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid AvailbilityRequest availbilityRequest, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Availbility availbility = new Availbility();
        availbility.setUsername(user);
        availbility.setDayOfTheWeek(availbilityRequest.getDayOfTheWeek());
        availbility.setHourBegin(availbilityRequest.getHourBegin());
        availbility.setHourEnd(availbilityRequest.getHourEnd());

        user = usersService.getWithAvailbilities(user);
        if(user.getAvailbilities() == null)
            user.setAvailbilities(new ArrayList<>());
        user.addAvailbility(availbility);

        availbilityService.save(availbility);

        AvailbilityResponse availbilityResponse = new AvailbilityResponse();
        availbilityResponse.setMessage("addAvailbility");
        availbilityResponse.setStatus(HttpStatus.OK.value());
        availbilityResponse.setTimeStamp(System.currentTimeMillis());
        availbilityResponse.setElementId(availbility.getId());

        return new ResponseEntity<>(availbilityResponse, HttpStatus.OK);
    }
}
