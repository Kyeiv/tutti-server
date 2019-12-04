package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.com.tutti.tuttiserver.entity.UserDetails;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    @Query("select d from UserDetails d where d.mail = ?1")
    List<UserDetails> searchForEmail(String mail);
}
