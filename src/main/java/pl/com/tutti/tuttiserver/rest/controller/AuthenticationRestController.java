package pl.com.tutti.tuttiserver.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Authorities;
import pl.com.tutti.tuttiserver.entity.privatekey.AuthoritiesPK;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.request.RegistrationForm;
import pl.com.tutti.tuttiserver.rest.response.RegisterResponse;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;
import java.security.Principal;
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

	@GetMapping("/whoami")
	public Principal getPrincipal(Principal principal) {

		return principal;
	}

	@PostMapping("/registration")
	public ResponseEntity registration(
			@Valid @RequestBody RegistrationForm registrationForm
			) {

		Users registered = new Users();

		AuthoritiesPK authoritiesPK = new AuthoritiesPK(registrationForm.getUsername(), registrationForm.getAuthority());
		Authorities authoritie = new Authorities(authoritiesPK);
		List<Authorities> authorities = new ArrayList<>();
		authorities.add(authoritie);
		registered.setAuthorities(authorities);

		registered.setEnabled(Boolean.TRUE);

		registered.setUsername(registrationForm.getUsername());
		registered.setPassword("{noop}" + registrationForm.getPassword());

		UserDetails userDetails = new UserDetails().builder()
				.city(registrationForm.getCity())
				.country(registrationForm.getCountry())
				.mail(registrationForm.getMail())
				.name(registrationForm.getName())
				.surname(registrationForm.getSurname())
				.phone(registrationForm.getPhone())
				.build();

		registered.setUserdDetails(userDetails);

		usersService.save(registered);

		RegisterResponse registerResponse = new RegisterResponse();
		registerResponse.setMessage("Registered succesfully!");
		registerResponse.setStatus(HttpStatus.OK.value());
		registerResponse.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(registerResponse, HttpStatus.OK);
	}
}
