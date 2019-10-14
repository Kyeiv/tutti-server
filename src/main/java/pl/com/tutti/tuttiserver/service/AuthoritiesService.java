package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.AuthoritiesRepository;

@Service
@AllArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;

}
