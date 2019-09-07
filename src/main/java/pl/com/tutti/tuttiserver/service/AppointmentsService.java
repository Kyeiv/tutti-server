package pl.com.tutti.tuttiserver.service;

import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.AppointmentsRepository;

@Service
public class AppointmentsService {
    private final AppointmentsRepository appointmentsRepository;

    public AppointmentsService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }
}
