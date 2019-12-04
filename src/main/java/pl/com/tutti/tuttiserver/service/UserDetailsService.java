package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.UserDetailsRepository;

@Service
@AllArgsConstructor
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public boolean mailExists(String mail) {
        val detailsWithMail = userDetailsRepository.searchForEmail(mail);
        if(detailsWithMail == null || detailsWithMail.size() == 0)
            return false;

        return  true;
    }
}
