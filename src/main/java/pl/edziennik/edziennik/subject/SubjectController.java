package pl.edziennik.edziennik.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.security.LoggedUser;

@Controller
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectRepository subjectRepository;
    private final LoggedUser loggedUser;

    public SubjectController(SubjectRepository subjectRepository,
                             LoggedUser loggedUser) {
        this.subjectRepository = subjectRepository;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/{subjectId}/schoolClasses")
    public String schoolClasses(@PathVariable Long subjectId, Model model) {
        if (!loggedUser.teachesSubject(subjectId)) {
            return "error/403";
        }
        model.addAttribute("subject", subjectRepository.getReferenceById(subjectId));
        return "subject/schoolClasses";
    }
}
