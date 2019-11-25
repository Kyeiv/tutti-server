package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.entity.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);

    @Query("select usrs, spec from Users usrs, UserDetails ud, Specialization spec " +
            "where usrs.userDetails.id = ud.id " +
            "and spec.username = usrs.username " +
            "and ud.city = ?1 " +
            "and spec.name = ?2 " +
            "and spec.level = ?3")
    List<Object[]> findByCityAndSpecNameAndSpecLevel(String city, String specName, String specLevel);
}
