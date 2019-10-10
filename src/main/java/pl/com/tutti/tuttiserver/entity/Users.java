package pl.com.tutti.tuttiserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    private String username;

    private String password;

    private Boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetails userdDetails;

    @OneToMany(mappedBy = "tutor")
    private List<Appointments> appointmentsAsTutor;

    @OneToMany(mappedBy = "student")
    private List<Appointments> appointmentsAsStudent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Specializations> specializations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Availbility> availbility;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authoritiesPK.username")
    private List<Authorities> authorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Posts> posts;
}
