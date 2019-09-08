package pl.com.tutti.tuttiserver.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Authorities;
import pl.com.tutti.tuttiserver.entity.AuthoritiesPK;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.request.RegistrationForm;
import pl.com.tutti.tuttiserver.rest.response.RegisterResponse;
import pl.com.tutti.tuttiserver.service.UsersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping("/login")
	public String login(){
		return "Hello!";
	}

	@PostMapping("/registration")
	public String registration(
			@RequestBody RegistrationForm registrationForm
			) {

		Users registered = new Users();

		AuthoritiesPK authoritiesPK = new AuthoritiesPK(registrationForm.getUsername(), registrationForm.getAuthority());
		Authorities authoritie = new Authorities(authoritiesPK);
		List<Authorities> authorities = new ArrayList<>();
		authorities.add(authoritie);
		registered.setAuthorities(authorities);

		registered.setEnabled(Boolean.TRUE);

		registered.setUsername(registrationForm.getUsername());
		registered.setPassword(registrationForm.getPassword());

		UserDetails userDetails = new UserDetails().builder()
				.city(registrationForm.getCity())
				.country(registrationForm.getCountry())
				.mail(registrationForm.getMail())
				.name(registrationForm.getName())
				.surname(registrationForm.getSurname())
				.phone(registrationForm.getPhone())
				.build();

		registered.setUserdDetailId(userDetails);

		usersService.save(registered);

		return "Registered";
	}
}
