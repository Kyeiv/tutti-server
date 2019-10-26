package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.rest.data.PostData;

@Getter
@Setter
public class PostDataResponse extends BasicResponse{
    private PostData postData;
}
