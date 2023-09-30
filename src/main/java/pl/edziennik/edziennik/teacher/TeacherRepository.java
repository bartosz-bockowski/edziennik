package pl.edziennik.edziennik.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.security.user.User;

import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>, QuerydslPredicateExecutor<Teacher> {
    Optional<Teacher> findOneById(Long id);
    List<Teacher> findAllByUser(User user);
}
