package pl.edziennik.edziennik.attendance;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.student.Student;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AttendanceType type;
    @ManyToOne
    private Student student;
    @ManyToOne
    private LessonPlan lessonPlan;
}
