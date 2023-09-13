package pl.edziennik.edziennik.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.teacher.Teacher;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT e FROM Subject e WHERE :teacher MEMBER OF e.teachers")
    List<Subject> findAllWhichHaveTeacher(Teacher teacher);
}
