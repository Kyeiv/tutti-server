package pl.com.tutti.tuttiserver.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequest {
    private String city;
    private String country;
    private String mail;
    private String surname;
    private String name;
    private String phone;
}
