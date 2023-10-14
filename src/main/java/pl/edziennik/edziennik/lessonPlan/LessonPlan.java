package pl.edziennik.edziennik.lessonPlan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.classRoom.ClassRoom;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.teacher.Teacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class LessonPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private LessonHour lessonHour;
    @ManyToOne
    private ClassRoom classRoom;
    private LocalDate date;
    @ManyToOne
    private SchoolClass schoolClass;
    @ManyToOne
    private Subject subject;
    @OneToMany(mappedBy = "lesson")
    private List<Exam> exams;
    @ElementCollection
    private Map<Student, AttendanceType> attendance;
    private String topic;
    private Boolean completed = false;

    public Boolean hasActiveExams() {
        return !this.exams.stream().filter(Exam::getActive).toList().isEmpty();
    }

    public Exam getLastExam() {
        return this.exams.get(this.exams.size() - 1);
    }

    public String getFormattedDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
