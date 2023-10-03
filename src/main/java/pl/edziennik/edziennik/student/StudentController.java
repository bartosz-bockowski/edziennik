package pl.edziennik.edziennik.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.mark.MarkUtils;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.security.LoggedUser;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final LoggedUser loggedUser;
    public StudentController(StudentRepository studentRepository,
                             SubjectRepository subjectRepository,
                             LoggedUser loggedUser) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/{studentId}/marks")
    public String marks(@PathVariable Long studentId, Model model){
        if(!loggedUser.hasAccessToStudent(studentId)){
            return "error/403";
        }
        model.addAttribute("student",studentRepository.getReferenceById(studentId));
        model.addAttribute("markUtils", new MarkUtils());
        model.addAttribute("bdf", new DecimalFormat("#"));
        model.addAttribute("divisor",new BigDecimal(1));
        return "student/marks";
    }
    @GetMapping("/{studentId}/subjectMarks/{subjectId}")
    public String subjectMarks(@PathVariable Long studentId, @PathVariable Long subjectId, Model model){
        if(!loggedUser.hasAccessToStudentMarks(studentId,subjectId)){
            return "error/403";
        }
        model.addAttribute("subject",subjectRepository.getReferenceById(subjectId));
        model.addAttribute("student",studentRepository.getReferenceById(studentId));
        return "student/subjectMarks";
    }
}
