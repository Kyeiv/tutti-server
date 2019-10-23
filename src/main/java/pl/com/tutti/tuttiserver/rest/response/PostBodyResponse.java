package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.rest.request.PostRequest;

@Getter
@Setter
public class PostBodyResponse extends BasicResponse{
    private PostRequest postRequest;
}
