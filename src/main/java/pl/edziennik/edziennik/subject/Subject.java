package pl.edziennik.edziennik.subject;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    private List<Teacher> teachers;
    private boolean active = true;
}
