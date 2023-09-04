package pl.edziennik.edziennik.school.schoolClass;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/schoolclass")
public class SchoolClassAdminController {
    private final SchoolClassRepository schoolClassRepository;
    public SchoolClassAdminController(SchoolClassRepository schoolClassRepository){
        this.schoolClassRepository = schoolClassRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",schoolClassRepository.findAll(pageable));
        return "schoolclass/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("schoolclass",new SchoolClass());
        return "schoolclass/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid SchoolClass schoolClass, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("result",result);
            model.addAttribute("schoolclass",schoolClass);
            return "schoolclass/add";
        }
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/list";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id) {
        SchoolClass schoolClass = schoolClassRepository.getOne(id);
        schoolClass.setActive(!schoolClass.isActive());
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/list";
    }
}
