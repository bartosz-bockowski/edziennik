package pl.edziennik.edziennik.school.subject;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.school.teacher.Teacher;

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
}
