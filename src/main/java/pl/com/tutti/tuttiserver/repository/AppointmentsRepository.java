package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Appointment;

public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
}
