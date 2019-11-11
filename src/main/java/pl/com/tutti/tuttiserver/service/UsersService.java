package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.repository.UsersRepository;
import pl.com.tutti.tuttiserver.rest.data.SearchTeachersData;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public void save (Users user) {
        usersRepository.save(user);
    }

    public List<Users> findAll() {return usersRepository.findAll();}

    public Users findByUsername(String username) {
        Users user = usersRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("No username found!");

        return user;
    }

    public  Users getWithSpecializations(Users user) {
        user.getSpecializations();
        return user;
    }

    public Users getWithAvailbilities(Users user) {
        user.getAvailbilities();
        return user;
    }

    public Users getWithPosts(Users user) {
        user.getPosts();
        return user;
    }

    public Users getWithAppointmentsAsTutor(Users user) {
        user.getAppointmentsAsTutor();
        return user;
    }

    public Users getWithAppointmentsAsStudent(Users user) {
        user.getAppointmentsAsStudent();
        return user;
    }

    public List<Users> findByCityAndSpecNameAndSpecLevel(SearchTeachersData searchTeachersData) {
        List<Users> users = usersRepository.findByCityAndSpecNameAndSpecLevel(
                  searchTeachersData.getCity()
                , searchTeachersData.getSpecializationName()
                , searchTeachersData.getLevel()
        );

        return users;
    }
}
