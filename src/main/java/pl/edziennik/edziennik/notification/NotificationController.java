package pl.edziennik.edziennik.notification;

import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.exam.ExamRepository;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.mark.MarkRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.student.Student;

import jakarta.persistence.EntityManager;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class NotificationController {
    private final LoggedUser loggedUser;
    private final MessageSource messageSource;
    @Autowired
    private final EntityManager entityManager;
    private final MarkRepository markRepository;
    private final ExamRepository examRepository;
    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeFormatter dateHrefFormatter;

    public NotificationController(LoggedUser loggedUser,
                                  MessageSource messageSource,
                                  EntityManager entityManager,
                                  MarkRepository markRepository,
                                  ExamRepository examRepository) {
        this.loggedUser = loggedUser;
        this.messageSource = messageSource;
        this.entityManager = entityManager;
        this.markRepository = markRepository;
        this.examRepository = examRepository;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.dateHrefFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @ResponseBody
    @GetMapping("/getNotifications")
    public List getNotifications() {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Notification> notifications = new ArrayList<>();
        User user = loggedUser.getUser();
        Student student = user.getStudent();
        if (student != null) {
            List<Mark> marks = new ArrayList<>(student.getMarks());
            for (Mark mark : marks) {
                notifications.addAll(getHistory(mark, mark.getId()));
            }
        }
        if (student != null && student.getSchoolClass() != null) {
            List<Exam> exams = new ArrayList<>(student.getSchoolClass().getLessonPlan().stream().flatMap(s -> s.getExams().stream()).filter(Objects::nonNull).toList());
            for (Exam exam : exams) {
                notifications.addAll(getHistory(exam, exam.getId()));
            }
        }
        //lessonplan changes history should be made by creating new entity lessonplanchange
        notifications = notifications.stream().sorted(Comparator.comparing(Notification::getSent).reversed()).toList();
        List<NotificationSimple> result = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationSimple notificationSimple = new NotificationSimple();
            switch (notification.getType()) {
                case NEW_MARK -> {
                    notificationSimple.setTitle(messageSource.getMessage("notification.newMark.title", null, LocaleContextHolder.getLocale()));
                    notificationSimple.setHref("/mark/" + notification.getTargetId() + "/history");
                    Mark mark = markRepository.getReferenceById(notification.getTargetId());
                    notificationSimple.setMessage(messageSource.getMessage("notification.newMark.message", new Object[]{
                            mark.getTeacher().getFullName(),
                            mark.getStudent().getFullName(),
                            mark.getMarkString(),
                            mark.getMarkCategory().getSubject().getName(),
                            mark.getMarkCategory().getName()
                    }, LocaleContextHolder.getLocale()));
                }
                case EDITED_MARK -> {
                    notificationSimple.setTitle(messageSource.getMessage("notification.editedMark.title", null, LocaleContextHolder.getLocale()));
                    notificationSimple.setHref("/mark/" + notification.getTargetId() + "/history");
                    List<Notification> markHistory = notifications.stream().filter(f -> (f.getType() == NotificationType.EDITED_MARK || f.getType() == NotificationType.NEW_MARK) && Objects.equals(f.getTargetId(), notification.getTargetId())).toList();
                    Long oldMarkId = markHistory.get(markHistory.indexOf(notification) + 1).getTargetId();
                    String oldMark = markRepository.getReferenceById(oldMarkId).getMarkString();
                    Mark mark = markRepository.getReferenceById(notification.getTargetId());
                    notificationSimple.setMessage(messageSource.getMessage("notification.editedMark.message", new Object[]{
                            mark.getTeacher().getFullName(),
                            mark.getStudent().getFullName(),
                            mark.getMarkCategory().getSubject().getName(),
                            mark.getMarkCategory().getName(),
                            mark.getMarkString(),
                            oldMark
                    }, LocaleContextHolder.getLocale()));
                }
                case NEW_EXAM -> {
                    Exam exam = examRepository.getReferenceById(notification.getTargetId());
                    notificationSimple.setTitle(messageSource.getMessage("notification.newExam.title", null, LocaleContextHolder.getLocale()));
                    notificationSimple.setHref("/schoolclass/" + exam.getLesson().getSchoolClass().getId() + "/lessonPlan?date=" + exam.getLesson().getDate().format(dateHrefFormatter));
                    notificationSimple.setMessage(messageSource.getMessage("notification.newExam.message", new Object[]{
                            exam.getTeacher().getFullName(),
                            exam.getLesson().getSchoolClass().getName(),
                            exam.getLesson().getSubject().getName(),
                            exam.getName(),
                            exam.getLesson().getDate().format(dateTimeFormatter)
                    }, LocaleContextHolder.getLocale()));
                }
                case CANCELLED_EXAM -> {
                    Exam exam = examRepository.getReferenceById(notification.getTargetId());
                    notificationSimple.setTitle(messageSource.getMessage("notification.cancelledExam.title", null, LocaleContextHolder.getLocale()));
                    notificationSimple.setHref("/schoolclass/" + exam.getLesson().getSchoolClass().getId() + "/lessonPlan?date=" + exam.getLesson().getDate().format(dateHrefFormatter));
                    notificationSimple.setMessage(messageSource.getMessage("notification.cancelledExam.message", new Object[]{
                            exam.getTeacher().getFullName(),
                            exam.getLesson().getSchoolClass().getName(),
                            exam.getLesson().getSubject().getName(),
                            exam.getName(),
                            exam.getLesson().getDate().format(dateTimeFormatter)
                    }, LocaleContextHolder.getLocale()));
                }
            }
            result.add(notificationSimple);
        }
        return result;
    }

    public <T> List<Notification> getHistory(T arg, Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(arg.getClass(), false, false);
        query.add(AuditEntity.id().eq(id));
        List<Object[]> results = query.getResultList();
        List<Notification> mainResult = new ArrayList<>();
        for (Object[] result : results) {
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) result[1];
            NotificationType notificationType = getNotificationType(arg, result);
            mainResult.add(new Notification(LocalDateTime.ofInstant(Instant.ofEpochMilli(revisionEntity.getTimestamp()), TimeZone.getDefault().toZoneId()), notificationType, id));
        }
        return mainResult;
    }

    private static <T> NotificationType getNotificationType(T arg, Object[] result) {
        RevisionType revType = (RevisionType) result[2];
        NotificationType notificationType = null;
        if (revType == RevisionType.ADD) {
            if (arg.getClass().equals(Mark.class)) {
                notificationType = NotificationType.NEW_MARK;
            }
            if (arg.getClass().equals(Exam.class)) {
                notificationType = NotificationType.NEW_EXAM;
            }
        } else if (revType == RevisionType.MOD) {
            if (arg.getClass().equals(Mark.class)) {
                notificationType = NotificationType.EDITED_MARK;
            }
            if (arg.getClass().equals(Exam.class)) {
                notificationType = NotificationType.CANCELLED_EXAM;
            }
        }
        return notificationType;
    }
}
