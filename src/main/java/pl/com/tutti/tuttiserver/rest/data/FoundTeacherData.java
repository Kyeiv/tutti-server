package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.tutti.tuttiserver.entity.Specialization;
import pl.com.tutti.tuttiserver.entity.Users;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoundTeacherData {
    Users user;
    Specialization specialization;
}
