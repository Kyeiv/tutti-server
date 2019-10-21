package pl.com.tutti.tuttiserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tutti.tuttiserver.entity.Post;
import pl.com.tutti.tuttiserver.repository.PostsRepository;
import pl.com.tutti.tuttiserver.rest.exception.PostNotExistsException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    public void save(Post post) {
        postsRepository.save(post);
    }

    public Post findById(Integer id) {
        Optional<Post> post = postsRepository.findById(id);
        if(!post.isPresent())
            throw new PostNotExistsException("This post doesn't exist!");

        return post.get();
    }

    public void delete(Post post) {
        postsRepository.delete(post);
    }
}
