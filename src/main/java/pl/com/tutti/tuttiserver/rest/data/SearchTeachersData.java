package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchTeachersData {
    @NotNull
    private String city;
    @NotNull
    private String level;
    @NotNull
    private String specializationName;
}
