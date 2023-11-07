package pl.edziennik.edziennik.subject;

import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.schoolClass.QSchoolClass;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.teacher.QTeacher;
import pl.edziennik.edziennik.teacher.Teacher;
import pl.edziennik.edziennik.teacher.TeacherRepository;

@Controller
@RequestMapping("/admin/subject")
public class SubjectAdminController {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    public SubjectAdminController(SubjectRepository subjectRepository,
                                  TeacherRepository teacherRepository,
                                  SchoolClassRepository schoolClassRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.schoolClassRepository = schoolClassRepository;
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
    public String details(Model model, @PathVariable Long id, @QuerydslPredicate(root = Teacher.class) Predicate teacherPredicate,
                          @QuerydslPredicate(root = SchoolClass.class) Predicate schoolClassPredicate){
        Subject subject = subjectRepository.getReferenceById(id);
        BooleanBuilder teacherBuilder = new BooleanBuilder();
        QTeacher qTeacher = QTeacher.teacher;
        teacherBuilder.and(qTeacher.subjects.contains(subject).not());
        teacherBuilder.and(teacherPredicate);
        BooleanBuilder schoolClassBuilder = new BooleanBuilder();
        QSchoolClass qSchoolClass = QSchoolClass.schoolClass;
        schoolClassBuilder.and(qSchoolClass.subjects.contains(subject).not());
        schoolClassBuilder.and(schoolClassPredicate);
        model.addAttribute("subject",subject);
        model.addAttribute("teachers",teacherRepository.findAll(teacherBuilder));
        model.addAttribute("schoolClasses",schoolClassRepository.findAll(schoolClassBuilder));
        return "subject/details";
    }
    @GetMapping("/{subjectId}/addTeacher")
    public String addTeacher(@PathVariable Long subjectId, @RequestParam Long teacherId){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        subject.getTeachers().add(teacher);
        subjectRepository.save(subject);
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
    @GetMapping("/{subjectId}/removeTeacher")
    public String removeTeacher(@PathVariable Long subjectId, @RequestParam Long teacherId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        subject.getTeachers().remove(teacherRepository.getReferenceById(teacherId));
        subjectRepository.save(subject);
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
    @GetMapping("/{subjectId}/addSchoolClass")
    public String addSchoolClass(@PathVariable Long subjectId, @RequestParam Long schoolClassId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(schoolClassId);
        schoolClass.getSubjects().add(subjectRepository.getReferenceById(subjectId));
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
    @GetMapping("/{subjectId}/removeSchoolClass")
    public String removeSchoolClass(@PathVariable Long subjectId, @RequestParam Long schoolClassId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(schoolClassId);
        schoolClass.getSubjects().remove(subjectRepository.getReferenceById(subjectId));
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/subject/" + subjectId + "/details";
    }
}
