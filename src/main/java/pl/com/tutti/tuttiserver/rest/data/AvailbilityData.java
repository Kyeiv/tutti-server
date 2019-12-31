package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailbilityData {
    @NotNull
    LocalTime hourBegin;
    @NotNull
    LocalTime hourEnd;
    Integer id;
    @NotNull
    Integer dayOfTheWeek;
}
