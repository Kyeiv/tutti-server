package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.UserDetails;

@Setter
@Getter
public class UserDetailsResponse extends BasicResponse{
    private UserDetails userDetails;
}
