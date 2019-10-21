package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.Post;

import java.util.List;

@Getter
@Setter
public class PostsResponse extends BasicResponse {
    private List<Post> posts;
}
