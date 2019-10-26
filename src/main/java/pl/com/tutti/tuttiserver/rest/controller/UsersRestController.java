package pl.com.tutti.tuttiserver.rest.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.service.UsersService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UsersRestController {
	
	private UsersService usersService;
	
	@GetMapping("/users")
	public List<Users> findAll(){
		return usersService.findAll();
	}
}
