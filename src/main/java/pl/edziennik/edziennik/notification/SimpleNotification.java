package pl.edziennik.edziennik.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleNotification {
    private String title;
    private String message;
    private String href;
}
