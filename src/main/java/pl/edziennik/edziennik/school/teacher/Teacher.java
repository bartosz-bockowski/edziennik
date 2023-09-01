package pl.edziennik.edziennik.school.teacher;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.enums.EmployeeType;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String firstName;
    private String lastName;
    private EmployeeType employeeType;
    private boolean active;
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
