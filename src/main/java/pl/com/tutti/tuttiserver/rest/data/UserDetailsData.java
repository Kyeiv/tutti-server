package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsData {
    @Pattern(regexp="[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_ ]{2,}", message = "Invalid city!")
    private String city;
    @Pattern(regexp="[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_]{2,}", message = "Invalid country!")
    private String country;
    @Email(message = "Email not valid!")
    private String mail;
    @Pattern(regexp="^[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]+(([',. -][a-zA-Z ])?[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]*)*$", message = "Invalid surname!")
    private String surname;
    @Pattern(regexp="^[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]+(([',. -][a-zA-Z zżźćńółęąśŻŹĆĄŚĘŁÓŃ])?[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]*)*$", message = "Invalid name!")
    private String name;
    @Pattern(regexp="[0-9]{9}", message = "Invalid phone!")
    private String phone;
}
