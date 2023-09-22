package pl.edziennik.edziennik.schoolClass;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@SchoolClassName
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @ManyToMany
    private List<Teacher> supervisingTeachers;
    @ManyToMany
    private List<Subject> subjects;
    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students;
    private boolean active = true;
}
