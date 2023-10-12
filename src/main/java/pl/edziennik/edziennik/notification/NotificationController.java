package pl.edziennik.edziennik.notification;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.security.LoggedUser;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotificationController {
    private final LoggedUser loggedUser;
    private final MessageSource messageSource;

    public NotificationController(LoggedUser loggedUser,
                                  MessageSource messageSource) {
        this.loggedUser = loggedUser;
        this.messageSource = messageSource;
    }

    @ResponseBody
    @GetMapping("/getNotifications")
    public List getNotifications() {
        return new ArrayList();
    }
}
