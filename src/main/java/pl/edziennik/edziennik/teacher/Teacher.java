package pl.edziennik.edziennik.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
//    @JoinTable(name = "teacher_users", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
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
    public String getFullName(){
        return firstName + " " + lastName;
    }
    public String getFullNameWithId(){
        return firstName + " " + lastName + " (ID: " + id + ")";
    }
    public void removeSupervisedSchoolClass(SchoolClass schoolClass){
        schoolClass.getSupervisingTeachers().remove(this);
        this.supervisedClasses.remove(schoolClass);
    }
}
