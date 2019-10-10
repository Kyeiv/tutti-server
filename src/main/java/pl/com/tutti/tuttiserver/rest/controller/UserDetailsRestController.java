package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.service.UserDetailsService;
import pl.com.tutti.tuttiserver.service.UsersService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserDetailsRestController {

    private final UsersService usersService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/user-details/{username}")
    public UserDetails getUserDetails(@PathVariable("username") String username){
        Users user = usersService.findByUsername(username);
        return user.getUserdDetails();
    }
}
