package pl.edziennik.edziennik.teacher;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/teacher")
public class TeacherAdminController {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    public TeacherAdminController(TeacherRepository teacherRepository,
                                  SubjectRepository subjectRepository,
                                  UserRepository userRepository){
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
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
    @GetMapping("{id}/details")
    public String details(@PathVariable Long id, Model model){
        Teacher teacher = teacherRepository.getReferenceById(id);
        model.addAttribute("teacher",teacher);
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("subjects",subjectRepository.findAllWhichHaveTeacher(teacher));
        return "teacher/details";
    }
    @GetMapping("/{teacherId}/addUser")
    public String setUser(@PathVariable Long teacherId, @RequestParam Long user){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        User userObj = userRepository.getReferenceById(user);
        List<User> users = teacher.getUsers();
        if(!users.contains(userObj)){
            users.add(userObj);
        }
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }
    @GetMapping("/{teacherId}/removeUser/{userId}")
    public String clearUser(@PathVariable Long teacherId, @PathVariable Long userId){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        User user = userRepository.getReferenceById(userId);
        teacher.getUsers().remove(user);
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/" + teacherId +"/details";
    }
}
