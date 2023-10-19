package pl.edziennik.edziennik.exam;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.lesson.LessonRepository;
import pl.edziennik.edziennik.security.LoggedUser;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/exam")
public class ExamController {
    private final ExamRepository examRepository;
    private final LessonRepository lessonPlanRepository;
    private final LoggedUser loggedUser;

    public ExamController(ExamRepository examRepository,
                          LessonRepository lessonPlanRepository,
                          LoggedUser loggedUser) {
        this.examRepository = examRepository;
        this.lessonPlanRepository = lessonPlanRepository;
        this.loggedUser = loggedUser;
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
        exam.setTeacher(loggedUser.getUser().getTeacher());
        exam.setCreated(LocalDateTime.now());
        examRepository.save(exam);
        return "redirect:/teacher/" + teacherId + "/lessonPlan?date=" + date;
    }

    @GetMapping("/delete/{examId}")
    public String delete(@PathVariable Long examId, @RequestParam Long teacherId, @RequestParam String date) {
        Exam exam = examRepository.getReferenceById(examId);
        exam.setTeacher(loggedUser.getUser().getTeacher());
        exam.setActive(false);
        examRepository.save(exam);
        return "redirect:/teacher/" + teacherId + "/lessonPlan?date=" + date;
    }
}
