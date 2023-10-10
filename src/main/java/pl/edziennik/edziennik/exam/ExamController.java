package pl.edziennik.edziennik.exam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.lessonPlan.LessonPlanRepository;

@Controller
@RequestMapping("/exam")
public class ExamController {
    private final ExamRepository examRepository;
    private final LessonPlanRepository lessonPlanRepository;
    public ExamController(ExamRepository examRepository,
                          LessonPlanRepository lessonPlanRepository) {
        this.examRepository = examRepository;
        this.lessonPlanRepository = lessonPlanRepository;
    }
    @GetMapping("/addExam/{lessonId}")
    public String addExam(@PathVariable Long lessonId, Model model){
        Exam exam = new Exam();
        exam.setLesson(lessonPlanRepository.getReferenceById(lessonId));
        model.addAttribute("exam", exam);
        return "exam/add";
    }
}
