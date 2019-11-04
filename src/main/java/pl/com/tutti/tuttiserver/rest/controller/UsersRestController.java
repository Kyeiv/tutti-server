package pl.com.tutti.tuttiserver.rest.controller;

import java.security.Principal;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Specialization;
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

	@PostMapping("/users/search")
	public ResponseEntity searchByCitySpecLevelSpecName(@Valid @RequestBody SearchTeachersData searchTeachersData){
		List<Users> users = usersService.findByCityAndSpecNameAndSpecLevel(searchTeachersData);

		for(Users user: users){
			user.setSpecializations(null);
			user.setAvailbilities(null);
			user.setPosts(null);
			user.setAppointmentsAsTutor(null);
		}

		return ResponseFactory.createPayloadResponse(users, "searchByCitySpecLevelSpecName");
	}
}
