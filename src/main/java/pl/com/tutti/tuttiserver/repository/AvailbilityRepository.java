package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Availbility;

public interface AvailbilityRepository extends JpaRepository<Availbility, Integer> {
}
