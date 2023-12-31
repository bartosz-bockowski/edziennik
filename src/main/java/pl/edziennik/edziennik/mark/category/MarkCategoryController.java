package pl.edziennik.edziennik.mark.category;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.subject.SubjectRepository;

@Controller
@RequestMapping("/markCategory")
public class MarkCategoryController {
    private final MarkCategoryRepository markCategoryRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final LoggedUser loggedUser;

    public MarkCategoryController(MarkCategoryRepository markCategoryRepository,
                                  SchoolClassRepository schoolClassRepository,
                                  SubjectRepository subjectRepository,
                                  LoggedUser loggedUser) {
        this.markCategoryRepository = markCategoryRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.loggedUser = loggedUser;
    }

    @PostMapping("/create")
    public String create(@Valid MarkCategory markCategory, BindingResult result, Model model) {
        if (!loggedUser.teachesSubject(markCategory.getSubject().getId())) {
            return "error/403";
        }
        if (result.hasErrors()) {
            model.addAttribute("markCategory", markCategory);
            model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(markCategory.getSchoolClass().getId()));
            model.addAttribute("subject", subjectRepository.getReferenceById(markCategory.getSubject().getId()));
            model.addAttribute("markCategories", markCategoryRepository.findAllBySchoolClassIdAndSubjectId(markCategory.getSchoolClass().getId(), markCategory.getSubject().getId()));
            return "schoolclass/marks";
        }
        markCategoryRepository.save(markCategory);
        return "redirect:/schoolclass/" + markCategory.getSchoolClass().getId() + "/marks/" + markCategory.getSubject().getId();
    }
}
