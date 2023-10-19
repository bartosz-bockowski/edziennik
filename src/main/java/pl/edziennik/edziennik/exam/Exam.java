package pl.edziennik.edziennik.exam;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import jakarta.persistence.*;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.teacher.Teacher;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime created;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Lesson lesson;
    private Boolean active = true;
}
