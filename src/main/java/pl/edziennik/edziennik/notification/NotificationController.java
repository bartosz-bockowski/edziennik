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
        List<Notification> notifications = loggedUser.getUser().getNotifications();
        List<SimpleNotification> result = new ArrayList<>();
        for (Notification notification : notifications) {
            SimpleNotification simpleNotification = new SimpleNotification();
            switch (notification.getType()) {
                case NEW_MARK -> {
                    Mark mark = notification.getMark();
                    simpleNotification.setTitle(messageSource.getMessage("notification.newMark.title", null, LocaleContextHolder.getLocale()));
                    simpleNotification.setMessage(messageSource.getMessage("notification.newMark.message", new Object[]{
                            mark.getTeacher().getFullName(),
                            mark.getStudent().getFullName(),
                            mark.getMarkString(),
                            mark.getMarkCategory().getSubject().getName(),
                            mark.getMarkCategory().getName()
                    }, LocaleContextHolder.getLocale()));
                    simpleNotification.setHref("/mark/history/" + mark.getMarkCategory().getId() + "/" + mark.getStudent().getId());
                }
                case EDITTED_MARK -> {
                    Mark mark = notification.getMark();
                    simpleNotification.setTitle(messageSource.getMessage("notification.newMark.title", null, LocaleContextHolder.getLocale()));
                    simpleNotification.setMessage(messageSource.getMessage("notification.edittedMark.message", new Object[]{
                            mark.getTeacher().getFullName(),
                            mark.getStudent().getFullName(),
                            mark.getMarkString(),
                            mark.getMarkCategory().getSubject().getName(),
                            mark.getMarkCategory().getName()
                    }, LocaleContextHolder.getLocale()));
                    simpleNotification.setHref("/mark/history/" + mark.getMarkCategory().getId() + "/" + mark.getStudent().getId());
                }
            }
            result.add(simpleNotification);
        }
        return result;
    }
}
