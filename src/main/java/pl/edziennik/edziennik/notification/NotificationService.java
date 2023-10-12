package pl.edziennik.edziennik.notification;

import lombok.Getter;
import org.aspectj.weaver.ast.Not;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
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

    private void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public NotificationService(NotificationRepository notificationRepository,
                               LoggedUser loggedUser) {
        this.notificationRepository = notificationRepository;
        this.loggedUser = loggedUser;
    }

    public void createAndSend(NotificationType type, List<User> addressees, String... messageArgs) {
        Notification notification = new Notification();
        notification.setAddressees(addressees);
        notification.setSender(loggedUser.getUser());
    }
}
