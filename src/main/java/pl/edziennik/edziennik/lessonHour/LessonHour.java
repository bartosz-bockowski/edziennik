package pl.edziennik.edziennik.lessonHour;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lesson.Lesson;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class LessonHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @OneToMany(mappedBy = "lessonHour")
    private List<Lesson> lessons;
    private int number = 1;
    private boolean active = true;
}
