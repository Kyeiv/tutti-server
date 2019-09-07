package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
