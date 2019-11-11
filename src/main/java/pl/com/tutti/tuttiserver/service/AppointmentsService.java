package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Appointment;
import pl.com.tutti.tuttiserver.repository.AppointmentsRepository;

@Service
@AllArgsConstructor
public class AppointmentsService {
    private final AppointmentsRepository appointmentsRepository;

    public void save(Appointment appointment) {
        appointmentsRepository.save(appointment);
    }
}
