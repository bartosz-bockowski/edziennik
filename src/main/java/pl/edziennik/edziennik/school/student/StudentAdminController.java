package pl.edziennik.edziennik.school.student;

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
import pl.edziennik.edziennik.school.parent.ParentRepository;

@Controller
@RequestMapping("/admin/student")
public class StudentAdminController {
    private final StudentRepository studentRepository;
    public StudentAdminController(StudentRepository studentRepository,
                                  ParentRepository parentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",studentRepository.findAll(pageable));
        return "student/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("student", new Student());
        return "student/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid Student student, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("result",result);
            model.addAttribute("student",student);
            return "student/add";
        }
        studentRepository.save(student);
        return "redirect:/admin/student/list";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        Student student = studentRepository.getOne(id);
        student.setActive(!student.isActive());
        studentRepository.save(student);
        return "redirect:/admin/student/list";
    }
    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("student",studentRepository.getReferenceById(id));
        return "student/details";
    }
}