package pl.edziennik.edziennik.school.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;



@EnableJpaRepositories
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
