package pl.edziennik.edziennik.student;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.attendance.Attendance;
import pl.edziennik.edziennik.attendance.AttendanceRepository;
import pl.edziennik.edziennik.attendance.QAttendance;
import pl.edziennik.edziennik.mark.MarkUtils;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.security.LoggedUser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final LoggedUser loggedUser;
    private final AttendanceRepository attendanceRepository;

    public StudentController(StudentRepository studentRepository,
                             SubjectRepository subjectRepository,
                             LoggedUser loggedUser,
                             AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.loggedUser = loggedUser;
        this.attendanceRepository = attendanceRepository;
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
    public String attendance(@PathVariable Long studentId, @QuerydslPredicate(root = Attendance.class) Predicate predicate) {
        if (!loggedUser.hasAccessToStudent(studentId)) {
            return "error/403";
        }
        QAttendance qAttendance = QAttendance.attendance;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAttendance.student.id.eq(studentId));
        builder.and(qAttendance.lesson.date.before(LocalDate.now().plusDays(1)));
        builder.and(qAttendance.lesson.lessonHour.start.before(LocalTime.now()));
        builder.and(predicate);
        Iterable<Attendance> attendanceList = attendanceRepository.findAll(builder);
        System.out.println(attendanceList);
        return "redirect:/";
    }
}
