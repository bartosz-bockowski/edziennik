package pl.edziennik.edziennik.school.parent;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.school.student.Student;
import pl.edziennik.edziennik.school.student.StudentRepository;

@Controller
@RequestMapping("/admin/parent")
public class ParentAdminController {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    public ParentAdminController(ParentRepository parentRepository,
                                 StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
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
            model.addAttribute("result",result);
            model.addAttribute("parent",parent);
            return "parent/add";
        }
        parentRepository.save(parent);
        return "redirect:/admin/parent/list";
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
        return "parent/details";
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
}
