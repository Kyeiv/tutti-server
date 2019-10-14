package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.repository.PostsRepository;

@Service
@AllArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

}
