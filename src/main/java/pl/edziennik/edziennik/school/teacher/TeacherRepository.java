package pl.edziennik.edziennik.school.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.security.user.User;

import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findOneById(Long id);
    @Query("SELECT e FROM Teacher e WHERE :user MEMBER OF e.users")
    List<Teacher> findAllWhichHaveUser(User user);
}
