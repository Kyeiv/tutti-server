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
    private List<Availbility> availbility;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authoritiesPK.username")
    private List<Authorities> authorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Post> posts;

    public void addSpecialization(Specialization specialization) {
        this.specializations.add(specialization);
        specialization.setUsername(this);
    }

    public void appointmentAsTutor(Appointment appointment) {
        this.appointmentsAsTutor.add(appointment);
        appointment.setTutor(this);
    }

    public void appointmentAsStudent(Appointment appointment) {
        this.appointmentsAsStudent.add(appointment);
        appointment.setStudent(this);
    }
}
