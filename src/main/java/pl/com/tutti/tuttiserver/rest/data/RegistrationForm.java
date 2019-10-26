package pl.com.tutti.tuttiserver.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    //@Pattern(regexp="/^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$/", message = "Invalid username!")
    private String username;
    private String password;
    private String authority;
    //@Pattern(regexp="/^[A-Za-z]+,[ ]?[A-Za-z]+{2,}$/", message = "Invalid city!")
    private String city;
    //@Pattern(regexp="/^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$/", message = "Invalid country!")
    private String country;
    @Email(message = "Email not valid!")
    private String mail;
    private String name;
    private String surname;
    private String phone;
}
