package pl.edziennik.edziennik.school.teacher;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            model.addAttribute("result",result);
            return "teacher/add";
        }
        teacher.setActive(true);
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/list";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        Teacher teacher = teacherRepository.getOne(id);
        teacher.setActive(!teacher.isActive());
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/list";
    }
    @GetMapping("/checkIfTeacherExists/{id}")
    public ResponseEntity<Boolean> checkIfTeacherExists(@PathVariable Long id){
        return new ResponseEntity<>(teacherRepository.findOneById(id).isPresent(), HttpStatus.OK);
    }
}
