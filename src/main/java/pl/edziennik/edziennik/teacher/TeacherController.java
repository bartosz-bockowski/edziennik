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
import pl.edziennik.edziennik.lesson.LessonPlanRepository;
import pl.edziennik.edziennik.lesson.LessonPlanService;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.subject.SubjectRepository;

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
    private final LessonPlanRepository lessonPlanRepository;
    private final LessonHourRepository lessonHourRepository;
    private final LessonPlanService lessonPlanService;
    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SchoolClassRepository schoolClassRepository;

    public TeacherController(TeacherRepository teacherRepository,
                             LoggedUser loggedUser,
                             LessonPlanRepository lessonPlanRepository,
                             LessonHourRepository lessonHourRepository,
                             LessonPlanService lessonPlanService,
                             SubjectRepository subjectRepository,
                             ClassRoomRepository classRoomRepository,
                             SchoolClassRepository schoolClassRepository) {
        this.teacherRepository = teacherRepository;
        this.loggedUser = loggedUser;
        this.lessonPlanRepository = lessonPlanRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.lessonPlanService = lessonPlanService;
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
        if (date == null) {
            date = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        } else {
            date = date.minusDays(date.getDayOfWeek().getValue() - 1);
        }
        List<LocalDate> dates = new ArrayList<>();
        dates.add(date);
        for (int i = 1; i < 5; i++) {
            dates.add(date.plusDays(i));
        }
        List<Lesson> lessons = lessonPlanRepository.getAllByTeacherIdAndDateInAndActiveTrue(teacherId, dates);
        model.addAttribute("lessons", lessons);
        List<LessonHour> hours = lessonHourRepository.findAllByActiveTrueOrderByStartAsc();
        model.addAttribute("hours", hours);
        model.addAttribute("now", LocalDateTime.now());
        List<List<Lesson>> plan = lessonPlanService.getPlan(hours, lessons, date);
        model.addAttribute("plan", plan);
        model.addAttribute("date", date);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        model.addAttribute("isTeacher", true);
        return "teacher/lessonPlan";
    }

    @GetMapping("/{teacherId}/supervisedClasses")
    public String supervisedClasses(@PathVariable Long teacherId, Model model) {
        model.addAttribute("teacher", teacherRepository.getReferenceById(teacherId));
        return "teacher/supervisedClasses";
    }

    @GetMapping("/{teacherId}/subjects")
    public String subjects(@PathVariable Long teacherId, Model model) {
        model.addAttribute("teacher", teacherRepository.getReferenceById(teacherId));
        return "teacher/subjects";
    }
}
