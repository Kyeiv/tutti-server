package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Availbility;
import pl.com.tutti.tuttiserver.repository.AvailbilityRepository;
import pl.com.tutti.tuttiserver.rest.exception.AvailbilityNotExistsException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AvailbilityService {
    private final AvailbilityRepository availbilityRepository;

    public void save(Availbility availbility) {
        availbilityRepository.save(availbility);
    }

    public Availbility findById(Integer id) {
        Optional<Availbility> availbility = availbilityRepository.findById(id);
        if(!availbility.isPresent())
            throw new AvailbilityNotExistsException("This availbility doesn't exist!");

        return availbility.get();
    }

    public void delete(Availbility availbility) {
        availbilityRepository.delete(availbility);
    }
}
