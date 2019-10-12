package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tutti.tuttiserver.entity.Specializations;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.response.RegisterResponse;
import pl.com.tutti.tuttiserver.rest.response.UserDetailsResponse;
import pl.com.tutti.tuttiserver.service.SpecializationsSerice;
import pl.com.tutti.tuttiserver.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SpecializationRestController {

    private final SpecializationsSerice specializationsSerice;
    private final UsersService usersService;

    @GetMapping("/specializations/{username}")
    List<Specializations> getSpecializations(@PathVariable("username") String username){

        Users user = usersService.findByUsername(username);
        user = usersService.getWithSpecializations(user);

        return user.getSpecializations();
    }
}
