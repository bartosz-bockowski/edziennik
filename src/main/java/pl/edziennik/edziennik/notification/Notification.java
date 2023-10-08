package pl.edziennik.edziennik.notification;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.teacher.Teacher;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User addressee;
    private String message;
    private NotificationType type;
    private LocalDateTime sent;
    private Boolean seen;
    @ManyToOne
    private Mark mark;
}
