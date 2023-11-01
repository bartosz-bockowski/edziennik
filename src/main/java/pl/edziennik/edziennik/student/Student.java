package pl.edziennik.edziennik.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.edziennik.edziennik.attendance.Attendance;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String secondName;
    private String lastName;
    @ManyToMany(mappedBy = "students")
    private List<Parent> parents = new ArrayList<>();
    private boolean active = true;
    @ManyToOne
    private SchoolClass schoolClass;
    private int groupInClass;
    @OneToMany(mappedBy = "student")
    private List<Mark> marks;
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendance;

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
        return this.firstName + " " + this.lastName;
    }

    public String getFullNameWithId() {
        return firstName + " " + lastName + " (ID: " + id + ")";
    }


    public Mark getMarkByMarkCategoryId(Long markCategoryId) {
        Mark mark = null;
        List<Mark> list = this.marks.stream().filter(p -> p.getMarkCategory().getId().equals(markCategoryId)).toList();
        if (!list.isEmpty()) {
            mark = list.get(0);
        }
        return mark;
    }

    public Boolean checkMark(Long markCategoryId) {
        Mark mark = null;
        List<Mark> list = this.marks.stream().filter(p -> p.getMarkCategory().getId().equals(markCategoryId)).toList();
        if (!list.isEmpty()) {
            mark = list.get(0);
        }
        return mark != null && mark.getActive();
    }

    public List<Mark> getMarksBySubjectId(Long subjectId) {
        return this.marks.stream().filter(p -> Objects.equals(p.getMarkCategory().getSubject().getId(), subjectId)).toList();
    }

    public Attendance getAttendanceByDateAndLessonHourId(LocalDate date, Long lessonHourId) {
        List<Attendance> r = this.attendance.stream().filter(f -> f.getLesson().getLessonHour().getId().equals(lessonHourId) && f.getLesson().getDate().equals(date)).toList();
        if (!r.isEmpty()) {
            return r.get(0);
        }
        return null;
    }
}
