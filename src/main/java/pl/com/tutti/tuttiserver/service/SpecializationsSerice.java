package pl.com.tutti.tuttiserver.service;

import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.SpecializationsRepository;

@Service
public class SpecializationsSerice {
    private final SpecializationsRepository specializationsRepository;

    public SpecializationsSerice(SpecializationsRepository specializationsRepository) {
        this.specializationsRepository = specializationsRepository;
    }
}
