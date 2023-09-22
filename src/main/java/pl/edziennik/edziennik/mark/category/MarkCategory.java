package pl.edziennik.edziennik.mark.category;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.subject.Subject;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class MarkCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private SchoolClass schoolClass;
    @ManyToOne
    private Subject subject;
    @Min(1)
    private int weight;
}
