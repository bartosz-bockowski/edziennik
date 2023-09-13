package pl.edziennik.edziennik.lessonPlan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.classRoom.ClassRoom;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.teacher.Teacher;

import java.time.LocalDate;

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
}
