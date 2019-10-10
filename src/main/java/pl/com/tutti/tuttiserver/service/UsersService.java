package pl.com.tutti.tuttiserver.service;

import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.repository.UsersRepository;

import javax.validation.constraints.Max;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

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
}
