package pl.edziennik.edziennik.school.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String firstName;
    @NotNull
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String lastName;
    private EmployeeType employeeType;
    private boolean active = true;
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
