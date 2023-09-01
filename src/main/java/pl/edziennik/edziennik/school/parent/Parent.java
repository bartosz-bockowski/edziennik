package pl.edziennik.edziennik.school.parent;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.school.student.Student;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<Student> students;
    @OneToOne
    private User user;
}
