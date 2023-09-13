package pl.edziennik.edziennik.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.security.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findOneById(Long id);
    List<Student> findAllBySchoolClassId(Long id);
    @Query("SELECT e FROM Student e WHERE :user MEMBER OF e.users")
    List<Student> findAllWhichHaveUser(User user);
}
