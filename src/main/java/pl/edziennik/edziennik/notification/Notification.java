package pl.edziennik.edziennik.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
    private LocalDateTime sent;
    private NotificationType type;
    private Long targetId;
}
