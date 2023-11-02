package pl.edziennik.edziennik.security.failedLoginAttempt;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.security.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface FailedLoginAttemptRepository extends JpaRepository<FailedLoginAttempt, Long> {
    int countAllByUserAndIpAndTimeAfter(User user, String ip, LocalDateTime time);
    @Transactional
    void deleteAllByUserAndIp(User user, String ip);
}
