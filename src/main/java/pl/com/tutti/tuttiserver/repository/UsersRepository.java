package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
}
