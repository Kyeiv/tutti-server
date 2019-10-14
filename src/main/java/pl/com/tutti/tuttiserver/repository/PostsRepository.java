package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Post;

public interface PostsRepository extends JpaRepository<Post, Integer> {
}
