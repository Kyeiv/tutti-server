package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Specializations;

public interface SpecializationsRepository extends JpaRepository<Specializations, Integer> {
}
