package pl.com.tutti.tuttiserver.rest.controller;

import java.security.Principal;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.data.SearchTeachersData;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UsersRestController {
	
	private UsersService usersService;

	@GetMapping("/users/search")
	public ResponseEntity searchByCitySpecLevelSpecName(@Valid @RequestBody SearchTeachersData searchTeachersData, Principal principal){
		List<Users> users = usersService.findByCityAndSpecNameAndSpecLevel(searchTeachersData);

		return ResponseFactory.createPayloadResponse(users, "searchByCitySpecLevelSpecName");
	}
}
