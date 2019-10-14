package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.AvailbilityRepository;

@Service
@AllArgsConstructor
public class AvailbilityService {
    private final AvailbilityRepository availbilityRepository;

}
