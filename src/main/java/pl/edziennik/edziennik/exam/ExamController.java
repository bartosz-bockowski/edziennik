package pl.edziennik.edziennik.exam;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.lessonPlan.LessonPlanRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

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

    @GetMapping("/add/{lessonId}")
    public String addExam(@PathVariable Long lessonId, Model model, @RequestParam Long teacherId, @RequestParam String date) {
        Exam exam = new Exam();
        exam.setLesson(lessonPlanRepository.getReferenceById(lessonId));
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("date", date);
        model.addAttribute("exam", exam);
        return "exam/add";
    }

    @PostMapping("/add")
    public String add(@Valid Exam exam, BindingResult result, Model model, @RequestParam String date, @RequestParam Long teacherId) {
        if (result.hasErrors()) {
            model.addAttribute("exam", exam);
            return "exam/add";
        }
        exam.setCreated(LocalDateTime.now());
        examRepository.save(exam);
        return "redirect:/teacher/" + teacherId + "/lessonPlan?date=" + date;
    }

    @GetMapping("/delete/{examId}")
    public String delete(@PathVariable Long examId, @RequestParam Long teacherId, @RequestParam String date) {
        examRepository.deleteById(examId);
        return "redirect:/teacher/" + teacherId + "/lessonPlan?date=" + date;
    }
}
