package pl.com.tutti.tuttiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tutti.tuttiserver.entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
}
