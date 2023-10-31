package pl.edziennik.edziennik.parent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parent")
public class ParentController {
    private final ParentRepository parentRepository;

    public ParentController(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @GetMapping("/{parentId}/students")
    public String students(@PathVariable Long parentId, Model model){
        model.addAttribute("parent",parentRepository.getReferenceById(parentId));
        return "parent/students";
    }
}
