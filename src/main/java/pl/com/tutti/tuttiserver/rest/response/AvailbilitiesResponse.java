package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.Availbility;

import java.util.List;

@Getter
@Setter
public class AvailbilitiesResponse extends BasicResponse{
    private List<Availbility> availbilities;
}
