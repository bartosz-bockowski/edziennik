package pl.edziennik.edziennik.notification;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.security.user.User;

import java.util.List;
@Controller
public class NotificationController {
    private final LoggedUser loggedUser;

    public NotificationController(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    @ResponseBody
    @GetMapping("/getNotifications")
    public List getNotifications(){
        return loggedUser.getUser().getNotifications();
    }
}
