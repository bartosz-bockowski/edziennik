package pl.edziennik.edziennik.school.schoolClass;

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
import pl.edziennik.edziennik.school.student.StudentStatus;

import java.util.Optional;

@Controller
@RequestMapping("/admin/schoolclass")
public class SchoolClassAdminController {
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    public SchoolClassAdminController(SchoolClassRepository schoolClassRepository,
                                      StudentRepository studentRepository){
        this.schoolClassRepository = schoolClassRepository;
        this.studentRepository = studentRepository;
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
    @GetMapping("/{id}/details")
    public String details(Model model, @PathVariable Long id){
        model.addAttribute("schoolClass",schoolClassRepository.getReferenceById(id));
        model.addAttribute("students",studentRepository.findAllBySchoolClassId(id));
        return "schoolclass/details";
    }
    @GetMapping("/{id}/checkStudent")
    public ResponseEntity<StudentStatus> checkStudent(@PathVariable Long id){
        Optional<Student> studentOpt = studentRepository.findOneById(id);
        if(studentOpt.isEmpty()){
            return new ResponseEntity<StudentStatus>(StudentStatus.NON_EXISTENT, HttpStatus.OK);
        } else {
            Student student = studentOpt.get();
            if(student.getSchoolClass() != null){
                return new ResponseEntity<StudentStatus>(StudentStatus.HAS_CLASS, HttpStatus.OK);
            } else {
                return new ResponseEntity<StudentStatus>(StudentStatus.NO_CLASS, HttpStatus.OK);
            }
        }
    }
    @GetMapping("/{id}/addStudent")
    public String addStudent(@PathVariable Long id, @RequestParam Long studentId){
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(schoolClassRepository.getReferenceById(id));
        studentRepository.save(student);
        return "redirect:/admin/schoolclass/" + id + "/details";
    }
}
