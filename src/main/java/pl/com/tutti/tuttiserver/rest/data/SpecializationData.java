package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationData {
    private Integer likes;
    private Integer dislikes;
    private String level;
    private String name;
    private Float salary;
    private Integer id;
}


