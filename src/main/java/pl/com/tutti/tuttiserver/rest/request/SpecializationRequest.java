package pl.com.tutti.tuttiserver.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationRequest {
    private Integer likes;
    private Integer dislikes;
    private String level;
    private String name;
    private Float salary;
    private Integer id;
}


