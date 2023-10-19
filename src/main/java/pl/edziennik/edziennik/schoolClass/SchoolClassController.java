package pl.edziennik.edziennik.schoolClass;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlanRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.lessonPlan.LessonPlanService;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.mark.category.MarkCategoryRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.lang.instrument.Instrumentation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schoolclass")
public class SchoolClassController {
    private final SchoolClassRepository schoolClassRepository;
    private final LessonPlanRepository lessonPlanRepository;
    private final LessonHourRepository lessonHourRepository;
    private final ClassRoomRepository classRoomRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final MarkCategoryRepository markCategoryRepository;
    private final LoggedUser loggedUser;
    private final LessonPlanService lessonPlanService;

    public SchoolClassController(SchoolClassRepository schoolClassRepository,
                                 LessonPlanRepository lessonPlanRepository,
                                 LessonHourRepository lessonHourRepository,
                                 ClassRoomRepository classRoomRepository,
                                 TeacherRepository teacherRepository,
                                 SubjectRepository subjectRepository,
                                 MarkCategoryRepository markCategoryRepository,
                                 LoggedUser loggedUser,
                                 LessonPlanService lessonPlanService) {
        this.schoolClassRepository = schoolClassRepository;
        this.lessonPlanRepository = lessonPlanRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.classRoomRepository = classRoomRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.markCategoryRepository = markCategoryRepository;
        this.loggedUser = loggedUser;
        this.lessonPlanService = lessonPlanService;
    }

    @GetMapping("/{classId}/lessonPlan")
    public String lessonPlan(Model model, @RequestParam(value = "date", required = false) LocalDate date, @PathVariable Long classId) {
        model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(classId));
        if (!loggedUser.hasAccessToSchoolClass(classId)) {
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
        List<LessonPlan> lessons = lessonPlanRepository.getAllBySchoolClassIdAndDateInAndActiveTrue(classId, dates);
        model.addAttribute("lessons", lessons);
        List<LessonHour> hours = lessonHourRepository.findAllByActiveTrueOrderByStartAsc();
        model.addAttribute("hours", hours);
        List<List<LessonPlan>> plan = lessonPlanService.getPlan(hours, lessons, date);
        model.addAttribute("plan", plan);
        model.addAttribute("date", date);
        model.addAttribute("isStudent", loggedUser.getUser().getStudent() != null);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if (loggedUser.hasAccessToSchoolClassAdmin(classId)) {
            model.addAttribute("subjects", subjectRepository.findAll());
            model.addAttribute("teachers", teacherRepository.findAll());
            model.addAttribute("classRooms", classRoomRepository.findAll());
            return "schoolclass/lessonPlanAdmin";
        }
        return "schoolclass/lessonPlan";
    }

    @GetMapping("/{classId}/marks/{subjectId}")
    public String marks(@PathVariable Long classId, @PathVariable Long subjectId, Model model) {
        model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(classId));
        model.addAttribute("markCategories", markCategoryRepository.findAllBySchoolClassIdAndSubjectId(classId, subjectId));
        model.addAttribute("subject", subjectRepository.getReferenceById(subjectId));
        model.addAttribute("markCategory", new MarkCategory());
        return "schoolclass/marks";
    }

    @GetMapping("/{schoolClassId}/getAverageMarkBySubjectId/{subjectId}")
    public ResponseEntity<String> getAvMark(@PathVariable Long schoolClassId, @PathVariable Long subjectId) {
        BigDecimal val = schoolClassRepository.getReferenceById(schoolClassId).getAverageMarkBySubjectId(subjectId);
        String result = val.setScale(2, RoundingMode.DOWN).toString();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
