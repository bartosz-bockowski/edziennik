package pl.edziennik.edziennik.subject;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "subjects")
    private List<SchoolClass> schoolClasses;
    @ManyToMany
    private List<Teacher> teachers = new ArrayList<>();
    @OneToMany(mappedBy = "subject")
    private List<MarkCategory> markCategories;
    private boolean active = true;

    public String getNameWithId(){
        return this.name + " (ID: " + this.id + ")";
    }
}
