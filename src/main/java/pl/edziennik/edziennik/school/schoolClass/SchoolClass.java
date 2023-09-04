package pl.edziennik.edziennik.school.schoolClass;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.school.teacher.Teacher;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@SchoolClassName
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @ManyToMany
    private List<Teacher> supervisingTeachers;
    private boolean active = true;
}
