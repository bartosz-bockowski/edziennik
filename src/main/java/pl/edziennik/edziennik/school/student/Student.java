package pl.edziennik.edziennik.school.student;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.school.schoolClass.SchoolClass;
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
    @OneToOne
    private User user;
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
