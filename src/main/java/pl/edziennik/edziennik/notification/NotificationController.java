package pl.edziennik.edziennik.notification;

import org.aspectj.weaver.ast.Not;
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
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
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

        List<Mark> marks = new ArrayList<>();
        if (student != null) {
            marks = student.getMarks();
        }
        for (Mark mark : marks) {
            List<Mark> markHistory = new ArrayList<>();
            HashMap<Object, LocalDateTime> history = getHistory(Mark.class, mark.getId());
            for (Object o : history.keySet()) {
                markHistory.add((Mark) o);
            }
            for (int i = 0; i < markHistory.size(); i++) {
                Mark target = markHistory.get(i);
                Notification notification = new Notification();
                if (i == 0) {
                    notification.setTitle(messageSource.getMessage("notification.newMark.title", null, LocaleContextHolder.getLocale()));
                    notification.setMessage(messageSource.getMessage("notification.newMark.message", new Object[]{
                            target.getTeacher().getFullName(),
                            target.getStudent().getFullName(),
                            target.getMarkString(),
                            target.getMarkCategory().getSubject().getName(),
                            target.getMarkCategory().getName()
                    }, LocaleContextHolder.getLocale()));
                } else {
                    notification.setTitle(messageSource.getMessage("notification.editedMark.title", null, LocaleContextHolder.getLocale()));
                    notification.setMessage(messageSource.getMessage("notification.editedMark.message", new Object[]{
                            target.getTeacher().getFullName(),
                            target.getStudent().getFullName(),
                            target.getMarkString(),
                            markHistory.get(i - 1).getMarkString(),
                            target.getMarkCategory().getSubject().getName(),
                            target.getMarkCategory().getName()
                    }, LocaleContextHolder.getLocale()));
                }
                notification.setSent(history.values().stream().toList().get(i));
                notification.setHref("/mark/" + target.getId() + "/history");
                notifications.add(notification);
            }
        }

        List<Exam> exams = new ArrayList<>();
        if (student != null && student.getSchoolClass() != null) {
            exams = student.getSchoolClass().getLessonPlan().stream().flatMap(s -> s.getExams().stream()).filter(Objects::nonNull).toList();
        }
        for (Exam exam : exams) {
            List<Exam> examHistory = new ArrayList<>();
            HashMap<Object, LocalDateTime> history = getHistory(Exam.class, exam.getId());
            for (Object o : history.keySet()) {
                examHistory.add((Exam) o);
            }
            for (int i = 0; i < examHistory.size(); i++) {
                Exam target = examHistory.get(i);
                Notification notification = new Notification();
                String part = "newExam";
                if (target.getActive().equals(false)) {
                    part = "cancelledExam";
                }
                notification.setTitle(messageSource.getMessage("notification." + part + ".title", null, LocaleContextHolder.getLocale()));
                notification.setMessage(messageSource.getMessage("notification." + part + ".message", new Object[]{
                        target.getTeacher().getFullName(),
                        target.getLesson().getSchoolClass().getName(),
                        target.getLesson().getSubject().getName(),
                        target.getName(),
                        target.getLesson().getFormattedDate()
                }, LocaleContextHolder.getLocale()));
                notification.setSent(history.values().stream().toList().get(i));
                notification.setHref("/schoolclass/" + target.getLesson().getSchoolClass().getId() + "/lessonPlan?date=" + target.getLesson().getDashDate());
                notifications.add(notification);
            }
        }
        for (Notification notification : notifications) {
            System.out.println(notification.getTitle());
            System.out.println(notification.getMessage());
            System.out.println();
        }
//        notifications = notifications.stream().sorted(Comparator.comparing(Notification::getSent).reversed()).toList();
//        List<NotificationSimple> result = new ArrayList<>();
//        for (Notification notification : notifications) {
//            NotificationSimple notificationSimple = new NotificationSimple();
//            switch (notification.getType()) {
//                case NEW_EXAM -> {
//                    Exam exam = examRepository.getReferenceById(notification.getTargetId());
//                    notificationSimple.setTitle(messageSource.getMessage("notification.newExam.title", null, LocaleContextHolder.getLocale()));
//                    notificationSimple.setHref("/schoolclass/" + exam.getLesson().getSchoolClass().getId() + "/lessonPlan?date=" + exam.getLesson().getDate().format(dateHrefFormatter));
//                    notificationSimple.setMessage(messageSource.getMessage("notification.newExam.message", new Object[]{
//                            exam.getTeacher().getFullName(),
//                            exam.getLesson().getSchoolClass().getName(),
//                            exam.getLesson().getSubject().getName(),
//                            exam.getName(),
//                            exam.getLesson().getDate().format(dateTimeFormatter)
//                    }, LocaleContextHolder.getLocale()));
//                }
//                case CANCELLED_EXAM -> {
//                    Exam exam = examRepository.getReferenceById(notification.getTargetId());
//                    notificationSimple.setTitle(messageSource.getMessage("notification.cancelledExam.title", null, LocaleContextHolder.getLocale()));
//                    notificationSimple.setHref("/schoolclass/" + exam.getLesson().getSchoolClass().getId() + "/lessonPlan?date=" + exam.getLesson().getDate().format(dateHrefFormatter));
//                    notificationSimple.setMessage(messageSource.getMessage("notification.cancelledExam.message", new Object[]{
//                            exam.getTeacher().getFullName(),
//                            exam.getLesson().getSchoolClass().getName(),
//                            exam.getLesson().getSubject().getName(),
//                            exam.getName(),
//                            exam.getLesson().getDate().format(dateTimeFormatter)
//                    }, LocaleContextHolder.getLocale()));
//                }
//                editted lesson
//            }
//            result.add(notificationSimple);
        return null;
    }

    public <T> LinkedHashMap<Object, LocalDateTime> getHistory(Class<?> cls, Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(cls, false, true);
        query.add(AuditEntity.id().eq(id));
        List<Object[]> results = query.getResultList();
        LinkedHashMap<Object, LocalDateTime> mainResult = new LinkedHashMap<>();
        for (Object[] result : results) {
            DefaultRevisionEntity entity = (DefaultRevisionEntity) result[1];
            mainResult.put(result[0], LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getTimestamp()), TimeZone.getDefault().toZoneId()));
        }
        return mainResult;
    }
}
