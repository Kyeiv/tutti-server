package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.repository.UsersRepository;

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
}
