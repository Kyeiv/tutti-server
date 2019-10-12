package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.request.RegistrationForm;
import pl.com.tutti.tuttiserver.rest.request.UserDetailsRequest;
import pl.com.tutti.tuttiserver.rest.response.BasicResponse;
import pl.com.tutti.tuttiserver.rest.response.UserDetailsResponse;
import pl.com.tutti.tuttiserver.service.UserDetailsService;
import pl.com.tutti.tuttiserver.service.UsersService;

import org.springframework.security.core.userdetails.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserDetailsRestController {

    private final UsersService usersService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/user-details")
    public ResponseEntity getUserDetails(Principal principal){
        Users user = usersService.findByUsername(principal.getName());

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setMessage("getUserDetails");
        userDetailsResponse.setStatus(HttpStatus.OK.value());
        userDetailsResponse.setTimeStamp(System.currentTimeMillis());
        userDetailsResponse.setUserDetails(user.getUserDetails());

        return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
    }

    @PutMapping("/user-details")
    public ResponseEntity setUserDetails(@Valid @RequestBody UserDetailsRequest userDetailsRequest, Principal principal){
        Users user = usersService.findByUsername(principal.getName());
        UserDetails userDetails = user.getUserDetails();

        userDetails.setCity(userDetailsRequest.getCity());
        userDetails.setCountry(userDetailsRequest.getCountry());
        userDetails.setMail(userDetailsRequest.getMail());
        userDetails.setSurname(userDetailsRequest.getSurname());
        userDetails.setName(userDetailsRequest.getName());
        userDetails.setPhone(userDetailsRequest.getPhone());

        usersService.save(user);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("setUserDetails");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
