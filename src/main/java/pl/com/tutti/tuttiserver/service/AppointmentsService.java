package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Appointment;
import pl.com.tutti.tuttiserver.repository.AppointmentsRepository;
import pl.com.tutti.tuttiserver.rest.exception.AppointmentNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentsService {
    private final AppointmentsRepository appointmentsRepository;

    public void save(Appointment appointment) {
        appointmentsRepository.save(appointment);
    }

    public Appointment findById(Integer id) {
        Optional<Appointment> appointment = appointmentsRepository.findById(id);
        if(!appointment.isPresent())
            throw new AppointmentNotFoundException("Appointment doesn't exist!");

        return  appointment.get();
    }

    public void delete(Appointment appointment) {
        appointmentsRepository.delete(appointment);
    }
}
