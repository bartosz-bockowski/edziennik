package pl.edziennik.edziennik.notification;

import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.exam.ExamRepository;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.mark.MarkRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.student.Student;

import jakarta.persistence.EntityManager;

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
    public List getNotifications(@RequestParam int count) {
        User user = loggedUser.getUser();
        Student student = user.getStudent();
        List<Notification> notifications = getStudentNotifications(student);
        if(user.getParent() != null){
            for (Student st : user.getParent().getStudents()){
                notifications.addAll(getStudentNotifications(st));
            }
        }
        int start = (count - 1) * 5;
        int end = count * 5;
        notifications = notifications.stream().sorted(Comparator.comparing(Notification::getSent).reversed()).toList();
        List<Notification> result = new ArrayList<>();
        for(int i = start; i < end; i++){
            try{
                result.add(notifications.get(i));
            } catch(ArrayIndexOutOfBoundsException e){
                break;
            }
        }
        return result;
    }

    public List<Notification> getStudentNotifications(Student student){
        List<Notification> notifications = new ArrayList<>();
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
                notification.setType(NotificationType.MARK);
                notification.setBootstrapColor("success");
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
            exams = student.getSchoolClass().getLesson().stream().flatMap(s -> s.getExams().stream()).filter(Objects::nonNull).toList();
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
                notification.setType(NotificationType.EXAM);
                notification.setBootstrapColor("warning");
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

        List<Lesson> lessons = new ArrayList<>();
        if (student != null && student.getSchoolClass() != null) {
            lessons = student.getSchoolClass().getLesson();
        }
        for (Lesson lesson : lessons) {
            List<Lesson> lessonHistory = new ArrayList<>();
            HashMap<Object, LocalDateTime> history = getHistory(Lesson.class, lesson.getId());
            for (Object o : history.keySet()) {
                lessonHistory.add((Lesson) o);
            }
            for (int i = 0; i < lessonHistory.size(); i++) {
                Lesson target = lessonHistory.get(i);
                Notification notification = new Notification();
                notification.setType(NotificationType.LESSON);
                notification.setBootstrapColor("primary");
                String part = "updatedLesson";
                if (target.getActive().equals(false)) {
                    part = "cancelledLesson";
                } else if (i == 0) {
                    part = "newLesson";
                }
                notification.setTitle(messageSource.getMessage("notification." + part + ".title", null, LocaleContextHolder.getLocale()));
                notification.setMessage(messageSource.getMessage("notification." + part + ".message", new Object[]{
                        target.getCreatedBy().getFullName(),
                        target.getSubject().getName(),
                        target.getSchoolClass().getName(),
                        target.getFormattedDate(),
                        target.getLessonHour().getStart(),
                        target.getLessonHour().getEnd()
                }, LocaleContextHolder.getLocale()));
                notification.setSent(history.values().stream().toList().get(i));
                notification.setHref("/schoolclass/" + target.getSchoolClass().getId() + "/lessonPlan?date=" + target.getDashDate());
                notifications.add(notification);
            }
        }

        return notifications;
    }

    public LinkedHashMap<Object, LocalDateTime> getHistory(Class<?> cls, Long id) {
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
