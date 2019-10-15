package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.repository.SpecializationsRepository;
import pl.com.tutti.tuttiserver.rest.exception.SpecializationNotExistsException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SpecializationsService {
    private final SpecializationsRepository specializationsRepository;

    public void save(Specialization specialization) {
        specializationsRepository.save(specialization);
    }

    public Specialization findById(Integer id) {
        Optional<Specialization> specialization = specializationsRepository.findById(id);
        if(!specialization.isPresent())
            throw new SpecializationNotExistsException("This specialization doesn't exst!");

        return specialization.get();
    }

    public void delete(Specialization specialization) {
        specializationsRepository.delete(specialization);
    }
}
