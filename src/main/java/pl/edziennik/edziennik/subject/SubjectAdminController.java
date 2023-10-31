package pl.edziennik.edziennik.subject;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.teacher.Teacher;
import pl.edziennik.edziennik.teacher.TeacherRepository;

@Controller
@RequestMapping("/admin/subject")
public class SubjectAdminController {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    public SubjectAdminController(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",subjectRepository.findAll(pageable));
        return "subject/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("subject", new Subject());
        return "subject/add";
    }
    @PostMapping("/add")
    public String add(Model model, @Valid Subject subject, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("subject",subject);
            model.addAttribute("result",result);
            return "subject/add";
        }
        subjectRepository.save(subject);
        return "redirect:/admin/subject/list";
    }
    @GetMapping("/{id}/details")
    public String details(Model model, @PathVariable Long id){
        model.addAttribute("subject",subjectRepository.getReferenceById(id));
        model.addAttribute("teachers",teacherRepository.findAll());
        return "subject/details";
    }
    @GetMapping("/{subjectId}/addTeacher")
    public String addTeacher(@RequestParam Long teacherId, @PathVariable Long subjectId){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        if(!subject.getTeachers().contains(teacher)){
            subject.getTeachers().add(teacher);
            subjectRepository.save(subject);
        }
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
    @GetMapping("/addTeacher")
    public ResponseEntity<?> addTeacherApi(@RequestParam(required = false) Long subject, @RequestParam(required = false) Long teacher){
        if(subject == null || teacher == null){
            return ResponseEntity.badRequest().build();
        }
        Teacher teacherObj = teacherRepository.getReferenceById(teacher);
        Subject subjectObj = subjectRepository.getReferenceById(subject);
        if(!subjectObj.getTeachers().contains(teacherObj)){
            subjectObj.getTeachers().add(teacherObj);
            subjectRepository.save(subjectObj);
        }
        return ResponseEntity.ok("");
    }
    @GetMapping("/removeTeacher")
    public ResponseEntity<?> removeTeacherApi(@RequestParam(required = false) Long subject, @RequestParam(required = false) Long teacher){
        if(subject == null || teacher == null){
            return ResponseEntity.badRequest().build();
        }
        Teacher teacherObj = teacherRepository.getReferenceById(teacher);
        Subject subjectObj = subjectRepository.getReferenceById(subject);
        subjectObj.getTeachers().remove(teacherObj);
        subjectRepository.save(subjectObj);
        return ResponseEntity.ok("");
    }
    @GetMapping("/{subjectId}/removeTeacher/{teacherId}")
    public String removeTeacher(@PathVariable Long subjectId, @PathVariable Long teacherId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        subject.getTeachers().remove(teacherRepository.getReferenceById(teacherId));
        subjectRepository.save(subject);
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
    @GetMapping("/checkIfSubjectExists/{id}")
    public ResponseEntity<Boolean> checkIfSubjectExists(@PathVariable Long id){
        return new ResponseEntity<>(subjectRepository.existsById(id), HttpStatus.OK);
    }
}
