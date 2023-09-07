package pl.edziennik.edziennik.school.student;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.school.parent.ParentRepository;
import pl.edziennik.edziennik.security.user.UserRepository;

@Controller
@RequestMapping("/admin/student")
public class StudentAdminController {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    public StudentAdminController(StudentRepository studentRepository,
                                  ParentRepository parentRepository,
                                  UserRepository userRepository){
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
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
        model.addAttribute("users",userRepository.findAll());
        return "student/details";
    }
    @GetMapping("/{studentId}/setUser")
    public String setUser(@PathVariable Long studentId, @RequestParam Long user){
        System.out.println(studentId);
        Student student = studentRepository.getReferenceById(studentId);
        student.setUser(userRepository.getReferenceById(user));
        studentRepository.save(student);
        return "redirect:/admin/student/" + studentId + "/details";
    }
    @GetMapping("/{studentId}/clearUser")
    public String clearUser(@PathVariable Long studentId){
        Student student = studentRepository.getReferenceById(studentId);
        student.setUser(null);
        studentRepository.save(student);
        return "redirect:/admin/student/" + studentId +"/details";
    }
}