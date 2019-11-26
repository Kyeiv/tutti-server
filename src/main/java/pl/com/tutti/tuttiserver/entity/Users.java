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
    private UserDetails userDetails;

    @OneToMany(mappedBy = "tutor")
    private List<Appointment> appointmentsAsTutor;

    @OneToMany(mappedBy = "student")
    private List<Appointment> appointmentsAsStudent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Specialization> specializations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Availbility> availbilities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authoritiesPK.username", fetch = FetchType.EAGER)
    private List<Authorities> authorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Post> posts;

    public void addAppointmentAsTutor(Appointment appointment) {
        this.appointmentsAsTutor.add(appointment);
        appointment.setTutor(this);
    }

    public void addAppointmentAsStudent(Appointment appointment) {
        this.appointmentsAsStudent.add(appointment);
        appointment.setStudent(this);
    }

    public void addSpecialization(Specialization specialization) {
        this.specializations.add(specialization);
        specialization.setUsername(this);
    }

    public void addAvailbility(Availbility availbility) {
        this.availbilities.add(availbility);
        availbility.setUsername(this);
    }

    public void addAuthority(Authorities authority) {
        this.authorities.add(authority);
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.setUsername(this);
    }
}
