package pl.com.tutti.tuttiserver.service;

import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.UserDetailsRepository;

@Service
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }
}
