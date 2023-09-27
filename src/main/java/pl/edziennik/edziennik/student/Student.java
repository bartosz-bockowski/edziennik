package pl.edziennik.edziennik.student;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;
    private String firstName;
    private String lastName;
    private boolean active = true;
    @ManyToOne
    private SchoolClass schoolClass;
    private int groupInClass;
    @OneToMany(mappedBy = "student")
    private List<Mark> marks;
    public String getFullName(){
        return this.firstName.concat(" ").concat(this.lastName);
    }
    public Mark getMarkByMarkCategoryId(Long markCategoryId){
        Mark mark = null;
        List<Mark> list = this.marks.stream().filter(p -> p.getMarkCategory().getId().equals(markCategoryId)).toList();
        if(!list.isEmpty()){
            mark = list.get(0);
        }
        return mark;
    }
    public List<Mark> getMarksBySubjectId(Long subjectId){
        return this.marks.stream().filter(p -> Objects.equals(p.getMarkCategory().getSubject().getId(), subjectId)).toList();
    }
}
