package pl.com.tutti.tuttiserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    private UserDetails userdDetailId;

    @OneToMany
    @JoinColumn(name = "tutor")
    private List<Appointments> appointmentsAsTutor;

    @OneToMany
    @JoinColumn(name = "student")
    private List<Appointments> appointmentsAsStudent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Specializations> specializations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Availbility> availbility;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Authorities> authorities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Posts> posts;
}
