package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewAppointmentData {
    @NotNull
    private List<LocalDateTime> dateTimes;
    @NotNull
    private String studentName;
    @NotNull
    private String teacherName;
}
