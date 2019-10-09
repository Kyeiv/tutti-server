package pl.com.tutti.tuttiserver.rest.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

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

    public RegistrationForm(
              String username
            , String password
            , String authority
            , String city
            , String country
            , String mail
            , String name
            , String surname
            , String phone
    ) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.city = city;
        this.country = country;
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public RegistrationForm() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }
}