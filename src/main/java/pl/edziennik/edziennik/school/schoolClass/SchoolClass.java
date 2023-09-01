package pl.edziennik.edziennik.school.schoolClass;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.school.teacher.Teacher;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Teacher> supervisingTeachers;
}
