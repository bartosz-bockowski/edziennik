package pl.edziennik.edziennik.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private LocalDateTime sent;
    private String title;
    private String message;
    private String href;

    @Override
    public String toString() {
        return "Notification{" +
                "sent=" + sent +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
