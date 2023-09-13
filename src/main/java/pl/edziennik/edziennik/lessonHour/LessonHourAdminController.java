package pl.edziennik.edziennik.lessonHour;

import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/lessonHour")
public class LessonHourAdminController {
    private final LessonHourRepository lessonHourRepository;
    public LessonHourAdminController(LessonHourRepository lessonHourRepository) {
        this.lessonHourRepository = lessonHourRepository;
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("hours",lessonHourRepository.findAll());
        return "lessonHour/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("lessonHour",new LessonHour());
        return "lessonHour/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid LessonHour lessonHour, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("lessonHour",lessonHour);
            model.addAttribute("result",result);
            return "lessonHour/add";
        }
        lessonHourRepository.save(lessonHour);
        return "redirect:/admin/lessonHour/list";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        LessonHour hour = lessonHourRepository.getReferenceById(id);
        hour.setActive(!hour.isActive());
        lessonHourRepository.save(hour);
        return "redirect:/admin/lessonHour/list";
    }
}
