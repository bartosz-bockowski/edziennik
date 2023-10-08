package pl.edziennik.edziennik.notification;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.student.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final LoggedUser loggedUser;
    public NotificationService(NotificationRepository notificationRepository,
                               LoggedUser loggedUser) {
        this.notificationRepository = notificationRepository;
        this.loggedUser = loggedUser;
    }

    public void createAndSend(Student student, Mark mark, NotificationType type){
        List<User> addressees = new ArrayList<>();
        addressees.add(student.getUser());
        addressees.addAll(student.getParents().stream().map(Parent::getUser).toList());
        for(User addressee : addressees){
            Notification notification = new Notification();
            notification.setSender(loggedUser.getUser());
            notification.setSent(LocalDateTime.now());
            notification.setSeen(false);
            notification.setAddressee(addressee);
            notification.setMessage("");
            notification.setType(type);
            notification.setMark(mark);
            notificationRepository.save(notification);
        }
    }
}
