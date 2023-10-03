package pl.edziennik.edziennik.mark;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.teacher.Teacher;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
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
    private LocalDateTime changed;
    public String getMarkString(){
        String result = String.valueOf(this.mark.intValue());
        if(!this.mark.remainder(new BigDecimal(1)).equals(new BigDecimal("0.00"))){
            return result.concat("+");
        }
        return result;
    }
}
