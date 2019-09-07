package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Appointments;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
}
