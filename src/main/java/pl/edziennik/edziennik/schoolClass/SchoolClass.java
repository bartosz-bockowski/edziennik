package pl.edziennik.edziennik.schoolClass;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.attendance.Attendance;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.mark.MarkUtils;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @ManyToMany(mappedBy = "supervisedClasses")
    private List<Teacher> supervisingTeachers = new ArrayList<>();
    @ManyToMany
    private List<Subject> subjects;
    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students;
    @OneToMany(mappedBy = "schoolClass")
    private List<LessonPlan> lessonPlan;
    private boolean active = true;

    public BigDecimal getAverageMarkBySubjectId(Long subjectId) {
        List<Mark> marks = this.students.stream().flatMap(student -> student.getMarks().stream()).filter(f -> f.getMarkCategory().getSubject().getId().equals(subjectId) && f.getActive()).toList();
        return new MarkUtils().countAverageOfMarks(marks);
    }
}
