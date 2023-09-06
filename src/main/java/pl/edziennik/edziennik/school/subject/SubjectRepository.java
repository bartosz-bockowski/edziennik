package pl.edziennik.edziennik.school.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.school.teacher.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT e FROM Subject e WHERE :teacher MEMBER OF e.teachers")
    List<Subject> findAllWhichHaveTeacher(Teacher teacher);
}
