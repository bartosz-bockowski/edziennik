package pl.edziennik.edziennik.exam;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime created;
    @OneToOne
    private LessonPlan lesson;
    private Boolean active = true;
}
