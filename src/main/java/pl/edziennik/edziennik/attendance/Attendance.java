package pl.edziennik.edziennik.attendance;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.student.Student;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AttendanceType type;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Lesson lesson;
}
