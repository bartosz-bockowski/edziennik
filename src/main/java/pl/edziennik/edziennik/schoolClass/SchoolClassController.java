package pl.edziennik.edziennik.schoolClass;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lesson.LessonRepository;
import pl.edziennik.edziennik.lesson.LessonService;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.mark.category.MarkCategoryRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;
import pl.edziennik.edziennik.utils.DateUtils;

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
    private final LessonRepository lessonRepository;
    private final LessonHourRepository lessonHourRepository;
    private final ClassRoomRepository classRoomRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final MarkCategoryRepository markCategoryRepository;
    private final LoggedUser loggedUser;
    private final LessonService lessonService;

    public SchoolClassController(SchoolClassRepository schoolClassRepository,
                                 LessonRepository lessonRepository,
                                 LessonHourRepository lessonHourRepository,
                                 ClassRoomRepository classRoomRepository,
                                 TeacherRepository teacherRepository,
                                 SubjectRepository subjectRepository,
                                 MarkCategoryRepository markCategoryRepository,
                                 LoggedUser loggedUser,
                                 LessonService lessonService) {
        this.schoolClassRepository = schoolClassRepository;
        this.lessonRepository = lessonRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.classRoomRepository = classRoomRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.markCategoryRepository = markCategoryRepository;
        this.loggedUser = loggedUser;
        this.lessonService = lessonService;
    }

    @GetMapping("/{classId}/lessonPlan")
    public String lessonPlan(Model model, @RequestParam(value = "date", required = false) LocalDate date, @PathVariable Long classId) {
        model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(classId));
        if (!loggedUser.hasAccessToAnyStudentOfSchoolClass(classId)) {
            return "error/403";
        }
        List<LocalDate> dates = new ArrayList<>();
        DateUtils dateUtils = new DateUtils();
        date = dateUtils.getDateAndListOfDatesForLessonPlan(date, dates);
        List<Lesson> lessons = lessonRepository.getAllBySchoolClassIdAndDateInAndActiveTrue(classId, dates);
        model.addAttribute("lessons", lessons);
        List<LessonHour> hours = lessonHourRepository.findAllByActiveTrueOrderByStartAsc();
        model.addAttribute("hours", hours);
        List<List<Lesson>> plan = lessonService.getPlan(hours, lessons, date);
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

    @GetMapping("/{schoolClassId}/marks/{subjectId}")
    public String marks(@PathVariable Long schoolClassId, @PathVariable Long subjectId, Model model) {
        if (!loggedUser.teachesSubject(subjectId) && !loggedUser.hasAccessToAnyStudentOfSchoolClass(schoolClassId)) {
            return "error/403";
        }
        if (loggedUser.teachesSubject(subjectId)) {
            model.addAttribute("manageMarks", true);
        }
        model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(schoolClassId));
        model.addAttribute("markCategories", markCategoryRepository.findAllBySchoolClassIdAndSubjectId(schoolClassId, subjectId));
        model.addAttribute("subject", subjectRepository.getReferenceById(subjectId));
        model.addAttribute("markCategory", new MarkCategory());
        return "schoolclass/marks";
    }

    @GetMapping("/{schoolClassId}/getAverageMarkBySubjectId/{subjectId}")
    public ResponseEntity<String> getAvMark(@PathVariable Long schoolClassId, @PathVariable Long subjectId) {
        if (!loggedUser.supervisesClass(schoolClassId) && !loggedUser.teachesSubject(subjectId) && !loggedUser.hasAccessToAnyStudentOfSchoolClass(schoolClassId)) {
            return null;
        }
        BigDecimal val = schoolClassRepository.getReferenceById(schoolClassId).getAverageMarkBySubjectId(subjectId);
        String result = val.setScale(2, RoundingMode.DOWN).toString();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
