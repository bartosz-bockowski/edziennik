package pl.edziennik.edziennik.notification;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.student.Student;

import jakarta.persistence.EntityManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Controller
public class NotificationController {
    private final LoggedUser loggedUser;
    private final MessageSource messageSource;
    @Autowired
    private final EntityManager entityManager;

    public NotificationController(LoggedUser loggedUser,
                                  MessageSource messageSource,
                                  EntityManager entityManager) {
        this.loggedUser = loggedUser;
        this.messageSource = messageSource;
        this.entityManager = entityManager;
    }

    @ResponseBody
    @GetMapping("/getNotifications")
    public List getNotifications() {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Notification> notifications = new ArrayList<>();
        User user = loggedUser.getUser();
        Student student = user.getStudent();
        if (student != null) {
            System.out.println("OCENY");
            List<Mark> marks = new ArrayList<>(student.getMarks());
            for (Mark mark : marks) {
                System.out.println(getHistory(mark, mark.getId()));
            }
        }
        if (student != null && student.getSchoolClass() != null) {
            System.out.println("EGZAMINY");
            List<Exam> exams = new ArrayList<>(student.getSchoolClass().getLessonPlan().stream().flatMap(s -> s.getExams().stream()).filter(Objects::nonNull).toList());
            for (Exam exam : exams) {
                System.out.println(getHistory(exam, exam.getId()));
            }
        }
        return new ArrayList();
    }

    public <T> List<Notification> getHistory(T arg, Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(arg.getClass(), false, false);
        query.add(AuditEntity.id().eq(id));
        List<Object[]> results = query.getResultList();
        List<Notification> mainResult = new ArrayList<>();
        for (Object[] result : results) {
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) result[1];
            RevisionType revType = (RevisionType) result[2];
            NotificationType notificationType = null;
            if (revType == RevisionType.ADD) {
                if (arg.getClass().equals(Mark.class)) {
                    notificationType = NotificationType.NEW_MARK;
                }
            } else if (revType == RevisionType.MOD) {
                if (arg.getClass().equals(Mark.class)) {
                    notificationType = NotificationType.EDITTED_MARK;
                }
            }
            mainResult.add(new Notification(LocalDateTime.ofInstant(Instant.ofEpochMilli(revisionEntity.getTimestamp()), TimeZone.getDefault().toZoneId()), notificationType, id));
        }
        return mainResult;
    }
}
