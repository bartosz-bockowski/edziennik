package pl.edziennik.edziennik.parent;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    private boolean active = true;

    //data
    private LocalDate dateOfBirth;
    private Boolean isMale;
    @PESEL
    private String pesel;
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "{validation.error.regex.wrongFormat}")
    private String postCode;
    private String city;
    private String street;
    private String homeNumber;
    private String apartment = "";
    @Email
    private String email;
    private Long phoneNumber;
    public String getFullName(){
        return this.firstName.concat(" ").concat(this.lastName);
    }
    public String getFullNameWithId(){
        return firstName + " " + lastName + " (ID: " + id + ")";
    }
}
