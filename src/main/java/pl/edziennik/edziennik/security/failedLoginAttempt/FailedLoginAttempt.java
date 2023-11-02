package pl.edziennik.edziennik.security.failedLoginAttempt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.security.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FailedLoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDateTime time;
    private String ip;
}
