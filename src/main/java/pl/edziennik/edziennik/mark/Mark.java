package pl.edziennik.edziennik.mark;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Teacher teacher;
    private LocalDateTime time;
    @DecimalMin(value = "0.0")
    @Digits(integer = 1, fraction = 1)
    private BigDecimal mark;
    @ManyToOne
    private MarkCategory markCategory;
}
