package pl.edziennik.edziennik.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.mark.MarkUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{studentId}/marks")
    public String marks(@PathVariable Long studentId, Model model){
        model.addAttribute("student",studentRepository.getReferenceById(studentId));
        model.addAttribute("markUtils", new MarkUtils());
        model.addAttribute("bdf", new DecimalFormat("#"));
        model.addAttribute("divisor",new BigDecimal(1));
        BigDecimal x = new BigDecimal("3.5");
        return "student/marks";
    }
    @GetMapping("/{studentId}/subjectMarks/{subjectId}")
    public String subjectMarks(@PathVariable Long studentId, @PathVariable Long subjectId, Model model){
        model.addAttribute("student",studentRepository.getReferenceById(studentId));
        return "mark/subjectMarks";
    }
}
