package pl.com.tutti.tuttiserver.service;

import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.AvailbilityRepository;

@Service
public class AvailbilityService {
    private final AvailbilityRepository availbilityRepository;

    public AvailbilityService(AvailbilityRepository availbilityRepository) {
        this.availbilityRepository = availbilityRepository;
    }
}
