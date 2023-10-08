package pl.edziennik.edziennik.security.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.edziennik.edziennik.notification.Notification;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.security.role.Role;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Username
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "user")
    private Student student;
    @OneToOne(mappedBy = "user")
    private Teacher teacher;
    @OneToOne(mappedBy = "user")
    private Parent parent;
    @Column(nullable = false, unique = true, length = 60)
    @Length(min = 6)
    private String username;
    @Length(min = 3)
    @NotNull
    private String password;
    private boolean active = true;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @Email
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "addressee")
    private List<Notification> notifications;
}
