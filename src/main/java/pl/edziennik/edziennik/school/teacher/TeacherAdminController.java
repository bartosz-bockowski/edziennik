package pl.edziennik.edziennik.school.teacher;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/teacher")
public class TeacherAdminController {
    private final TeacherRepository teacherRepository;
    public TeacherAdminController(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",teacherRepository.findAll(pageable));
        System.out.println(teacherRepository.findAll(pageable));
        return "teacher/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        Teacher teacher = new Teacher();
        model.addAttribute("teacher",teacher);
        return "teacher/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid Teacher teacher, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("teacher",teacher);
            return "teacher/add";
        }
        teacher.setActive(true);
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/list";
    }
}
