package pl.edziennik.edziennik.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectRepository subjectRepository;
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    @GetMapping("/{subjectId}/schoolClasses")
    public String schoolClasses(@PathVariable Long subjectId, Model model){
        model.addAttribute("subject",subjectRepository.getReferenceById(subjectId));
        return "subject/schoolClasses";
    }
}
