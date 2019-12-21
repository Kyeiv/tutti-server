package pl.com.tutti.tuttiserver.rest.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Appointment;
import pl.com.tutti.tuttiserver.entity.Availbility;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.data.*;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.service.AppointmentsService;
import pl.com.tutti.tuttiserver.service.UsersService;
import pl.com.tutti.tuttiserver.service.structures.Pair;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UsersRestController {
	
	private UsersService usersService;
	private AppointmentsService appointmentsService;

	@PostMapping("/users/search")
	public ResponseEntity searchByCitySpecLevelSpecName(@Valid @RequestBody SearchTeachersData searchTeachersData){
		List<Pair<Users, Specialization>> users = usersService.findByCityAndSpecNameAndSpecLevel(searchTeachersData);
		List<FoundTeacherData> foundTeachersData = new ArrayList<>();
		for(Pair<Users, Specialization> element: users){
			element.getKey().setSpecializations(null);
			element.getKey().setAvailbilities(null);
			element.getKey().setPosts(null);
			element.getKey().setAppointmentsAsTutor(null);
			element.getKey().setPassword(null);

			element.getValue().setUsername(null);
		}

		return ResponseFactory.createPayloadResponse(users, "searchByCitySpecLevelSpecName");
	}

	@PostMapping("/users/get-day-availbility")
	public ResponseEntity getAvailbilityAtGivenDay(@Valid @RequestBody TeacherDayData teacherDayData) {
		Integer dayOfTheWeek = teacherDayData.getDate().getDayOfWeek().getValue() - 1; //Monday = 0 etc.

		Users teacher = usersService.findByUsername(teacherDayData.getTeacherUsername());
		teacher = usersService.getWithAvailbilities(teacher);

		List<Availbility> availbilitiesAtGivenDay = teacher.getAvailbilities().stream()
				.filter( availbility ->  availbility.getDayOfTheWeek() == dayOfTheWeek).collect(Collectors.toList());

		Map<Long, Boolean> hoursAvailable = new HashMap<>();

		for(Availbility availbility: availbilitiesAtGivenDay) {
			long hours = availbility.getHourBegin().until(availbility.getHourEnd(), ChronoUnit.HOURS);
			long hourBegin = availbility.getHourBegin().getHour();
			long hourEnd = hourBegin + hours;

			for(long i = hourBegin; i < hourEnd ; i++)
				hoursAvailable.put(i,true);
		}

		teacher = usersService.getWithAppointmentsAsTutor(teacher);

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		List<Appointment> appointmentsAtGivenDay = teacher.getAppointmentsAsTutor().stream()
				.filter( appointment ->{
					cal1.setTime(Date.from(appointment.getScheduledDatetime().atZone(ZoneId.systemDefault()).toInstant()));
					cal2.setTime(Date.from(teacherDayData.getDate().atZone(ZoneId.systemDefault()).toInstant()));
					return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
							cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
				}).collect(Collectors.toList());

		for(Appointment appointment: appointmentsAtGivenDay) {
			long hour = appointment.getScheduledDatetime().getHour();
			long hours = 1;

			long hourEnd = hour + hours;

			for(long i = hour; i < hourEnd ; i++)
				hoursAvailable.put(i,false);
		}

		return ResponseFactory.createPayloadResponse(hoursAvailable, "getAvailbilityAtGivenDay");
	}

	@PostMapping("/users/appointment")
	ResponseEntity addNewAppointment(@Valid @RequestBody NewAppointmentData newAppointmentData) {

		Users student = usersService.findByUsername(newAppointmentData.getStudentName());
		student = usersService.getWithAppointmentsAsStudent(student);

		Users teacher = usersService.findByUsername(newAppointmentData.getTeacherName());
		teacher = usersService.getWithAppointmentsAsTutor(teacher);

		for(LocalDateTime dateTime: newAppointmentData.getDateTimes()) {
			Appointment appointment = new Appointment();

			val localDateTime = dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
			appointment.setScheduledDatetime(localDateTime);

			teacher.addAppointmentAsTutor(appointment);
			student.addAppointmentAsStudent(appointment);

			appointmentsService.save(appointment);
		}

		return ResponseFactory.createBasicResponse("addNewAppointment");
	}

	@GetMapping("users/my-appointments")
	ResponseEntity getMyAppointments(Principal principal, Authentication authentication) {

		val role = authentication.getAuthorities().iterator().next();
		val roleName = role.getAuthority();

		var user = usersService.findByUsername(principal.getName());

		if(roleName.equals("TEACHER")){
			user = usersService.getWithAppointmentsAsTutor(user);
			val appointmentsDataList = convertToAppointmentsListAsTeacher(user.getAppointmentsAsTutor());
			return ResponseFactory.createPayloadResponse(appointmentsDataList, "getMyAppointments");
		} else {
			user = usersService.getWithAppointmentsAsStudent(user);
			val appointmentsDataList = convertToAppointmentsListAsStudent(user.getAppointmentsAsStudent());
			return ResponseFactory.createPayloadResponse(appointmentsDataList, "getMyAppointments");
		}
	}

	@DeleteMapping("users/remove-appointment/{id}")
	ResponseEntity deleteAppointment(@PathVariable("id") Integer id, Principal principal) {

		var appointment = appointmentsService.findById(id);

		val studentName = appointment.getStudent().getUsername();
		val tutorName = appointment.getTutor().getUsername();

		if(!principal.getName().equals(studentName) && !principal.getName().equals(tutorName))
			throw new UnauthorizedException("You are not a teacher, nor a student in this appointment!");

		appointmentsService.delete(appointment);

		return ResponseFactory.createBasicResponse("deleteAppointment");
	}

	List<AppointmentData> convertToAppointmentsListAsTeacher(List<Appointment> appointments) {

		List<AppointmentData> newList = appointments.stream().map(
				a ->
					new AppointmentData(
							a.getId(),
							a.getStudent().getUserDetails().getSurname(),
							a.getStudent().getUserDetails().getName(),
							a.getStudent().getUsername(),
							a.getStudent().getUserDetails().getPhone(),
							a.getStudent().getUserDetails().getMail(),
							a.getScheduledDatetime()
					)
				).collect(Collectors.toList());

		return newList;
	}

	List<AppointmentData> convertToAppointmentsListAsStudent(List<Appointment> appointments) {

		List<AppointmentData> newList = appointments.stream().map(
				a ->
						new AppointmentData(
								a.getId(),
								a.getTutor().getUserDetails().getSurname(),
								a.getTutor().getUserDetails().getName(),
								a.getTutor().getUsername(),
								a.getTutor().getUserDetails().getPhone(),
								a.getTutor().getUserDetails().getMail(),
								a.getScheduledDatetime()
						)
		).collect(Collectors.toList());

		return newList;
	}
}
