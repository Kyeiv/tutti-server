package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailbilityData {
    LocalTime hourBegin;
    LocalTime hourEnd;
    Integer id;
    Integer dayOfTheWeek;
}
