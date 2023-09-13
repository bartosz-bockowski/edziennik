package pl.edziennik.edziennik.student;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;
    private String firstName;
    private String lastName;
    private boolean active = true;
    @ManyToOne
    private SchoolClass schoolClass;
    private int groupInClass;
    public String getFullName(){
        return this.firstName.concat(" ").concat(this.lastName);
    }
}
