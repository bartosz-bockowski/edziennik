package pl.edziennik.edziennik.lessonHour;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/lessonHour")
public class LessonHourAdminController {
    private final LessonHourRepository lessonHourRepository;
    private final MessageSource messageSource;
    public LessonHourAdminController(LessonHourRepository lessonHourRepository,
                                     MessageSource messageSource) {
        this.lessonHourRepository = lessonHourRepository;
        this.messageSource = messageSource;
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
        if(lessonHour.getStart().isAfter(lessonHour.getEnd())){
            result.addError(new FieldError("lessonHour","end",messageSource.getMessage("validation.error.lessonHour.endBeforeStart",null, LocaleContextHolder.getLocale())));
        }
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
