package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Authorities;
import pl.com.tutti.tuttiserver.entity.AuthoritiesPK;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesPK> {
}
