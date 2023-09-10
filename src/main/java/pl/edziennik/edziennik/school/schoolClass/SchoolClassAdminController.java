package pl.edziennik.edziennik.school.schoolClass;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.school.student.Student;
import pl.edziennik.edziennik.school.student.StudentRepository;
import pl.edziennik.edziennik.school.student.StudentStatus;
import pl.edziennik.edziennik.school.subject.Subject;
import pl.edziennik.edziennik.school.subject.SubjectRepository;

@Controller
@RequestMapping("/admin/schoolclass")
public class SchoolClassAdminController {
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    public SchoolClassAdminController(SchoolClassRepository schoolClassRepository,
                                      StudentRepository studentRepository, SubjectRepository subjectRepository){
        this.schoolClassRepository = schoolClassRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
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
        model.addAttribute("allStudents",studentRepository.findAll());
        model.addAttribute("subjects",subjectRepository.findAll());
        return "schoolclass/details";
    }
    @GetMapping("/{id}/addStudent")
    public String addStudent(@PathVariable Long id, @RequestParam Long studentId){
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(schoolClassRepository.getReferenceById(id));
        studentRepository.save(student);
        return "redirect:/admin/schoolclass/" + id + "/details";
    }
    @GetMapping("/{id}/removeStudent/{studentId}")
    public String removeStudent(@PathVariable Long id, @PathVariable Long studentId){
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(null);
        studentRepository.save(student);
        return "redirect:/admin/schoolclass/" + id + "/details";
    }
    @GetMapping("/{id}/addSubject")
    public String addSubject(@PathVariable Long id, @RequestParam Long subjectId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(id);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        if(!schoolClass.getSubjects().contains(subject)) {
            schoolClass.getSubjects().add(subject);
        }
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/" + id +"/details";
    }
    @GetMapping("{id}/removeSubject/{subjectId}")
    public String removeSubject(@PathVariable Long id, @PathVariable Long subjectId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(id);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        schoolClass.getSubjects().remove(subject);
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/" + id + "/details";
    }
}
