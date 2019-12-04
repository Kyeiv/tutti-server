package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Authorities;
import pl.com.tutti.tuttiserver.entity.privatekey.AuthoritiesPK;
import pl.com.tutti.tuttiserver.entity.UserDetails;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.data.RegistrationForm;
import pl.com.tutti.tuttiserver.rest.exception.EmailAlreadyExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UsernameAlreadyExistsException;
import pl.com.tutti.tuttiserver.service.UserDetailsService;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationRestController {

	private UsersService usersService;
	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;

	@GetMapping("/whoami")
	public Principal getPrincipal(Principal principal) {
		return principal;
	}

	@PostMapping("/registration")
	public ResponseEntity registration(
			@Valid @RequestBody RegistrationForm registrationForm
			) {

		if(usersService.usernameExists(registrationForm.getUsername()))
			throw new UsernameAlreadyExistsException("Username already exists!");

		if(userDetailsService.mailExists(registrationForm.getMail()))
			throw new EmailAlreadyExistsException("Email already exists!");

		Users registered = new Users();

		AuthoritiesPK authoritiesPK = new AuthoritiesPK(registrationForm.getUsername(), registrationForm.getAuthority());
		Authorities authoritie = new Authorities(authoritiesPK);
		List<Authorities> authorities = new ArrayList<>();
		authorities.add(authoritie);
		registered.setAuthorities(authorities);

		registered.setEnabled(Boolean.TRUE);

		registered.setUsername(registrationForm.getUsername());
		registered.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

		UserDetails userDetails = new UserDetails().builder()
				.city(registrationForm.getCity())
				.country(registrationForm.getCountry())
				.mail(registrationForm.getMail())
				.name(registrationForm.getName())
				.surname(registrationForm.getSurname())
				.phone(registrationForm.getPhone())
				.build();

		registered.setUserDetails(userDetails);

		usersService.save(registered);

		return ResponseFactory.createBasicResponse("registration");
	}
}
