package pl.edziennik.edziennik.parent;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.student.StudentRepository;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/parent")
public class ParentAdminController {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    public ParentAdminController(ParentRepository parentRepository,
                                 StudentRepository studentRepository,
                                 UserRepository userRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",parentRepository.findAll(pageable));
        return "parent/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("parent", new Parent());
        return "parent/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid Parent parent, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("parent",parent);
            return "parent/add";
        }
        Long parentId = parent.getId();
        if(parentId != null){
            parent.setStudents(parentRepository.getReferenceById(parentId).getStudents());
        }
        return "redirect:/admin/parent/" + parentRepository.save(parent).getId() + "/details";
    }
    @GetMapping("/{parentId}/edit")
    public String edit(@PathVariable Long parentId, Model model){
        model.addAttribute("parent",parentRepository.getReferenceById(parentId));
        return "parent/add";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        Parent parent = parentRepository.getReferenceById(id);
        parent.setActive(!parent.isActive());
        parentRepository.save(parent);
        return "redirect:/admin/parent/list";
    }
    @GetMapping("/{id}/details")
    public String details(Model model, @PathVariable Long id){
        model.addAttribute("parent",parentRepository.getReferenceById(id));
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("freeUsers",userRepository.findAllByStudentIsNullAndTeacherIsNullAndParentIsNull());
        return "parent/adminDetails";
    }
    @GetMapping("/{parentId}/addStudent")
    public String addStudent(@RequestParam Long studentId, @PathVariable Long parentId){
        Parent parent = parentRepository.getReferenceById(parentId);
        Student student = studentRepository.getReferenceById(studentId);
        if(!parent.getStudents().contains(student)){
            parent.getStudents().add(student);
            parentRepository.save(parent);
        }
        return "redirect:/admin/parent/" + parentId + "/details";
    }
    @GetMapping("/{id}/checkIfStudentExists")
    public ResponseEntity<Boolean> check(@PathVariable Long id){
        boolean result = studentRepository.existsById(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
    @GetMapping("/{parentId}/removeStudent/{studentId}")
    public String removeStudent(@PathVariable Long parentId, @PathVariable Long studentId){
        Parent parent = parentRepository.getReferenceById(parentId);
        parent.getStudents().remove(studentRepository.getReferenceById(studentId));
        parentRepository.save(parent);
        return "redirect:/admin/parent/" + parentId + "/details";
    }
    @GetMapping("/{parentId}/setUser")
    public String setUser(@PathVariable Long parentId, @RequestParam Long user){
        Parent parent = parentRepository.getReferenceById(parentId);
        if(parent.getUser() == null) {
            User userObj = userRepository.getReferenceById(user);
            parent.setUser(userObj);
            parentRepository.save(parent);
        }
        return "redirect:/admin/parent/" + parentId + "/details";
    }
    @GetMapping("/{parentId}/removeUser")
    public String clearUser(@PathVariable Long parentId){
        Parent parent = parentRepository.getReferenceById(parentId);
        parent.setUser(null);
        parentRepository.save(parent);
        return "redirect:/admin/parent/" + parentId +"/details";
    }
}
