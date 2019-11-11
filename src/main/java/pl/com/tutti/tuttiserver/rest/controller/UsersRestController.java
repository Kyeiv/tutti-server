package pl.com.tutti.tuttiserver.rest.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Appointment;
import pl.com.tutti.tuttiserver.entity.Availbility;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.data.NewAppointmentData;
import pl.com.tutti.tuttiserver.rest.data.SearchTeachersData;
import pl.com.tutti.tuttiserver.rest.data.TeacherDayData;
import pl.com.tutti.tuttiserver.service.AppointmentsService;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UsersRestController {
	
	private UsersService usersService;
	private AppointmentsService appointmentsService;

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
			long hours = appointment.getDurationMinutes();

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

			appointment.setDurationMinutes(1);
			val localDateTime = dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
			appointment.setScheduledDatetime(localDateTime);

			teacher.addAppointmentAsTutor(appointment);
			student.addAppointmentAsStudent(appointment);

			appointmentsService.save(appointment);
		}

		return ResponseFactory.createBasicResponse("addNewAppointment");
	}
}
