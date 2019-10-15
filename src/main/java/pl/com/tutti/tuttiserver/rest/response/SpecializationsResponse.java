package pl.com.tutti.tuttiserver.rest.response;

import lombok.Getter;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.Specialization;

import java.util.List;

@Getter
@Setter
public class SpecializationsResponse extends BasicResponse{
    private List<Specialization> specializations;
}
