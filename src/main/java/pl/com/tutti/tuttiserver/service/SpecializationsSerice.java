package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.SpecializationsRepository;

@Service
@AllArgsConstructor
public class SpecializationsSerice {
    private final SpecializationsRepository specializationsRepository;

}
