package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Specialization;

public interface SpecializationsRepository extends JpaRepository<Specialization, Integer> {
}
