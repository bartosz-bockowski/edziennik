package pl.edziennik.edziennik.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIdNot(String username, Long id);
    Long countAllByUsername(String username);
    Optional<User> findByRestorePasswordCode(String code);
    @Query("SELECT max(id) FROM User")
    Long getMaxId();
    List<User> findAllByStudentIsNullAndTeacherIsNullAndParentIsNull();
}
