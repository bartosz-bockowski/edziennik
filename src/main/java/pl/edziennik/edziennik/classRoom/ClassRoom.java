package pl.edziennik.edziennik.classRoom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;

import java.util.List;

@Entity
@Getter
@Setter
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @OneToMany(mappedBy = "classRoom")
    private List<LessonPlan> lessons;
    private boolean active;
}
