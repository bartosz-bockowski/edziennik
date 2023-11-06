package pl.edziennik.edziennik.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.attendance.AttendanceRepository;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.mark.MarkUtils;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.utils.DateUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final LoggedUser loggedUser;
    private final AttendanceRepository attendanceRepository;
    private final LessonHourRepository lessonHourRepository;

    public StudentController(StudentRepository studentRepository,
                             SubjectRepository subjectRepository,
                             LoggedUser loggedUser,
                             AttendanceRepository attendanceRepository,
                             LessonHourRepository lessonHourRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.loggedUser = loggedUser;
        this.attendanceRepository = attendanceRepository;
        this.lessonHourRepository = lessonHourRepository;
    }

    @GetMapping("/{studentId}/marks")
    public String marks(@PathVariable Long studentId, Model model) {
        if (!loggedUser.hasAccessToStudent(studentId)) {
            return "error/403";
        }
        model.addAttribute("student", studentRepository.getReferenceById(studentId));
        model.addAttribute("markUtils", new MarkUtils());
        model.addAttribute("bdf", new DecimalFormat("#"));
        model.addAttribute("divisor", new BigDecimal(1));
        return "student/marks";
    }

    @GetMapping("/{studentId}/subjectMarks/{subjectId}")
    public String subjectMarks(@PathVariable Long studentId, @PathVariable Long subjectId, Model model) {
        if (!loggedUser.hasAccessToStudentMarks(studentId, subjectId)) {
            return "error/403";
        }
        model.addAttribute("subject", subjectRepository.getReferenceById(subjectId));
        model.addAttribute("student", studentRepository.getReferenceById(studentId));
        return "student/subjectMarks";
    }

    @GetMapping("/{studentId}/attendance")
    public String attendance(@PathVariable Long studentId, Model model, @RequestParam(required = false) Integer period) {
        if (!loggedUser.hasAccessToStudent(studentId)) {
            return "error/403";
        }
        if (period == null) {
            period = 0;
        }
        model.addAttribute("student", studentRepository.getReferenceById(studentId));
        model.addAttribute("lessonHours", lessonHourRepository.findAll());
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        model.addAttribute("period", period);
        model.addAttribute("dates", new DateUtils().lastFifteenDays(LocalDate.now().plusDays(period * 15L)));
        return "student/attendance";
    }
    @GetMapping("{studentId}/details")
    public String details(@PathVariable Long studentId, Model model){
        if(!loggedUser.hasAccessToStudent(studentId)){
            return "error/403";
        }
        if(loggedUser.teacherSupervisesStudentsClass(studentId)){
            model.addAttribute("supervisor",true);
        }
        Student student = studentRepository.getReferenceById(studentId);
        model.addAttribute("student",student);
        return "student/details";
    }
}
