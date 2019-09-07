package pl.com.tutti.tuttiserver.service;

import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
