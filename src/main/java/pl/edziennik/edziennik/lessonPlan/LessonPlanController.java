package pl.edziennik.edziennik.lessonPlan;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lessonplan")
public class LessonPlanController {
    private final LessonPlanRepository lessonPlanRepository;

    public LessonPlanController(LessonPlanRepository lessonPlanRepository) {
        this.lessonPlanRepository = lessonPlanRepository;
    }

    @GetMapping("/{id}/create")
    public String create(@PathVariable Long id, Model model) {
        LessonPlan lessonPlan = lessonPlanRepository.getReferenceById(id);
        model.addAttribute("lessonPlan", lessonPlan);
        model.addAttribute("attendanceTypes", AttendanceType.values());
        return "lesson/create";
    }
}
