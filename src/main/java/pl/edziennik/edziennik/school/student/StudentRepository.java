package pl.edziennik.edziennik.school.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.school.schoolClass.SchoolClass;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findOneById(Long id);
    List<Student> findAllBySchoolClassId(Long id);
}
