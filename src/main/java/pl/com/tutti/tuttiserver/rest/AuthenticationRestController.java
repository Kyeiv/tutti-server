package pl.com.tutti.tuttiserver.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {
	
	@GetMapping("/login")
	public String login(){
		return "Hello!";
	}
}
