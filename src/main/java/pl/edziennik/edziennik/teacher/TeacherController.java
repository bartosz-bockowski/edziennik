package pl.edziennik.edziennik.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lesson.LessonRepository;
import pl.edziennik.edziennik.lesson.LessonService;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final LoggedUser loggedUser;
    private final LessonRepository lessonRepository;
    private final LessonHourRepository lessonHourRepository;
    private final LessonService lessonService;
    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SchoolClassRepository schoolClassRepository;

    public TeacherController(TeacherRepository teacherRepository,
                             LoggedUser loggedUser,
                             LessonRepository lessonRepository,
                             LessonHourRepository lessonHourRepository,
                             LessonService lessonService,
                             SubjectRepository subjectRepository,
                             ClassRoomRepository classRoomRepository,
                             SchoolClassRepository schoolClassRepository) {
        this.teacherRepository = teacherRepository;
        this.loggedUser = loggedUser;
        this.lessonRepository = lessonRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.lessonService = lessonService;
        this.subjectRepository = subjectRepository;
        this.classRoomRepository = classRoomRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @GetMapping("/{teacherId}/lessonPlan")
    public String lessonPlan(@PathVariable Long teacherId, Model model, @RequestParam(value = "date", required = false) LocalDate date) {
        model.addAttribute("teacher", teacherRepository.getReferenceById(teacherId));
        if (!loggedUser.hasAccessToTeacher(teacherId)) {
            return "error/403";
        }
        List<LocalDate> dates = new ArrayList<>();
        DateUtils dateUtils = new DateUtils();
        date = dateUtils.getDateAndListOfDatesForLessonPlan(date, dates);
        List<Lesson> lessons = lessonRepository.getAllByTeacherIdAndDateInAndActiveTrue(teacherId, dates);
        model.addAttribute("lessons", lessons);
        List<LessonHour> hours = lessonHourRepository.findAllByActiveTrueOrderByStartAsc();
        model.addAttribute("hours", hours);
        model.addAttribute("now", LocalDateTime.now());
        List<List<Lesson>> plan = lessonService.getPlan(hours, lessons, date);
        model.addAttribute("plan", plan);
        model.addAttribute("date", date);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        model.addAttribute("isTeacher", true);
        return "teacher/lessonPlan";
    }

    @GetMapping("/{teacherId}/supervisedClasses")
    public String supervisedClasses(@PathVariable Long teacherId, Model model) {
        if (!loggedUser.hasAccessToTeacher(teacherId)) {
            return "error/403";
        }
        model.addAttribute("teacher", teacherRepository.getReferenceById(teacherId));
        return "teacher/supervisedClasses";
    }

    @GetMapping("/{teacherId}/subjects")
    public String subjects(@PathVariable Long teacherId, Model model) {
        if (!loggedUser.hasAccessToTeacher(teacherId)) {
            return "error/403";
        }
        model.addAttribute("teacher", teacherRepository.getReferenceById(teacherId));
        return "teacher/subjects";
    }
}
