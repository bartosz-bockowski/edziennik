package pl.edziennik.edziennik.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;
import pl.edziennik.edziennik.subject.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String firstName;
    @NotNull
    private String lastName;
    @ManyToMany(mappedBy = "supervisingTeachers")
    private List<SchoolClass> supervisedClasses = new ArrayList<>();
    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons;
    private boolean active = true;
    @ManyToMany(mappedBy = "teachers")
    private List<Subject> subjects = new ArrayList<>();
    @OneToMany(mappedBy = "teacher")
    private List<Mark> marks;

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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFullNameWithId() {
        return firstName + " " + lastName + " (ID: " + id + ")";
    }

    public void removeSupervisedSchoolClass(SchoolClass schoolClass) {
        schoolClass.getSupervisingTeachers().remove(this);
        this.supervisedClasses.remove(schoolClass);
    }

    public Boolean isFree(LessonHour hour, LocalDate date) {
        return this.lessons.stream().noneMatch(f -> f.getLessonHour().getId().equals(hour.getId()) && f.getDate().equals(date) && f.getActive().equals(true));
    }
}
