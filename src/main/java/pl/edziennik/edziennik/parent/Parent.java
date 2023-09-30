package pl.edziennik.edziennik.parent;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.student.Student;
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
    @ManyToMany
    private List<User> users;
    private boolean active = true;
    public String getFullName(){
        return this.firstName.concat(" ").concat(this.lastName);
    }
    public String getFullNameWithId(){
        return firstName + " " + lastName + " (ID: " + id + ")";
    }
}
