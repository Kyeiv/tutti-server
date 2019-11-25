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
public class RegistrationForm {

    @Pattern(regexp="[A-Za-z0-9]{3,20}", message = "Invalid username!")
    private String username;
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&-_,\\.])[A-Za-z[0-9]@$!%*#?&-_,\\.]{5,64}", message = "Invalid password!")
    private String password;
//    @Pattern(regexp="/[A-Za-z0-9]{3,20}/", message = "Invalid authority!")
    private String authority;
    @Pattern(regexp="[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_ ]{2,}", message = "Invalid city!")
    private String city;
    @Pattern(regexp="[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_]{2,}", message = "Invalid country!")
    private String country;
    @Email(message = "Email not valid!")
    private String mail;
    @Pattern(regexp="^[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]+(([',. -][a-zA-Z zżźćńółęąśŻŹĆĄŚĘŁÓŃ])?[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]*)*$", message = "Invalid name!")
    private String name;
    @Pattern(regexp="^[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]+(([',. -][a-zA-Z ])?[a-zA-ZzżźćńółęąśŻŹĆĄŚĘŁÓŃ]*)*$", message = "Invalid surname!")
    private String surname;
    @Pattern(regexp="[0-9]{9}", message = "Invalid phone!")
    private String phone;
}
