package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.data.UserDetailsData;
import pl.com.tutti.tuttiserver.service.UserDetailsService;
import pl.com.tutti.tuttiserver.service.UsersService;

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

        return ResponseFactory.createPayloadResponse(user.getUserDetails(), "getUserDetails");
    }

    @PutMapping("/user-details")
    public ResponseEntity setUserDetails(@Valid @RequestBody UserDetailsData userDetailsData, Principal principal){
        Users user = usersService.findByUsername(principal.getName());
        UserDetails userDetails = user.getUserDetails();

        userDetails.setCity(userDetailsData.getCity());
        userDetails.setCountry(userDetailsData.getCountry());
        userDetails.setMail(userDetailsData.getMail());
        userDetails.setSurname(userDetailsData.getSurname());
        userDetails.setName(userDetailsData.getName());
        userDetails.setPhone(userDetailsData.getPhone());

        usersService.save(user);

        return ResponseFactory.createBasicResponse("setUserDetails");
    }
}
