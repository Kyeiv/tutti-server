package pl.com.tutti.tuttiserver.rest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.service.UsersService;

@RestController
@RequestMapping("/api")
public class UsersRestController {
	
	private UsersService usersService;

	@Autowired
	public UsersRestController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/users")
	public List<Users> findAll(){
		return usersService.findAll();
	}
}
