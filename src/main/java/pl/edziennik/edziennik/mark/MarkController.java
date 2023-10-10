package pl.edziennik.edziennik.mark;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.mark.category.MarkCategoryRepository;
import pl.edziennik.edziennik.notification.NotificationService;
import pl.edziennik.edziennik.notification.NotificationType;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.student.StudentRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mark")
public class MarkController {
    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final MarkCategoryRepository markCategoryRepository;
    private final TeacherRepository teacherRepository;
    @Autowired
    private final EntityManager entityManager;
    private final NotificationService notificationService;

    public MarkController(MarkRepository markRepository,
                          StudentRepository studentRepository,
                          MarkCategoryRepository markCategoryRepository,
                          TeacherRepository teacherRepository,
                          EntityManager entityManager,
                          NotificationService notificationService) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.markCategoryRepository = markCategoryRepository;
        this.teacherRepository = teacherRepository;
        this.entityManager = entityManager;
        this.notificationService = notificationService;
    }

    @ResponseBody
    @GetMapping("/add/{mark}/{studentId}/{markCategoryId}/{markId}")
    public ResponseEntity<Boolean> add(@PathVariable String mark, @PathVariable Long studentId, @PathVariable Long markCategoryId, @PathVariable String markId) {
        Mark markObj = new Mark();
        Student student = studentRepository.getReferenceById(studentId);
        markObj.setMark(new BigDecimal(mark));
        markObj.setStudent(student);
        markObj.setMarkCategory(markCategoryRepository.getReferenceById(markCategoryId));
        markObj.setTime(LocalDateTime.now());
        markObj.setTeacher(teacherRepository.getReferenceById(1L));
        markObj.setChanged(LocalDateTime.now());
        if (!markId.equals("null")) {
            markObj.setId(Long.parseLong(markId));
            notificationService.createAndSendNewMark(markObj, NotificationType.EDITTED_MARK);
        } else {
            notificationService.createAndSendNewMark(markObj, NotificationType.NEW_MARK);
        }
        markRepository.save(markObj);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/history/{categoryId}/{studentId}")
    public String history(@PathVariable Long categoryId, @PathVariable Long studentId, Model model) {
        Student student = studentRepository.getReferenceById(studentId);
        MarkCategory markCategory = markCategoryRepository.getReferenceById(categoryId);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Mark> history = new ArrayList<>();
        Mark mark = markRepository.getMarkByStudentIdAndMarkCategoryId(studentId, categoryId);
        if (mark != null) {
            List<Number> revisions = reader.getRevisions(mark.getClass(), mark.getId());
            for (Number revision : revisions) {
                history.add(reader.find(Mark.class, mark.getId(), revision));
            }
        }
        model.addAttribute("history", history);
        model.addAttribute("student", student);
        model.addAttribute("markCategory", markCategory);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss"));
        return "mark/history";
    }
}
