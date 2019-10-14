package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.UserDetailsRepository;

@Service
@AllArgsConstructor
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

}
