package pl.edziennik.edziennik.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
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
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String firstName;
    @NotNull
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String lastName;
    @ManyToMany
    private List<SchoolClass> supervisedClasses = new ArrayList<>();
    @OneToMany(mappedBy = "teacher")
    private List<LessonPlan> lessons;
    private boolean active = true;
    @ManyToMany(mappedBy = "teachers")
    List<Subject> subjects;

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
