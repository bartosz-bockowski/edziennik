package pl.edziennik.edziennik.teacher;

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
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/teacher")
public class TeacherAdminController {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    public TeacherAdminController(TeacherRepository teacherRepository,
                                  SubjectRepository subjectRepository,
                                  UserRepository userRepository,
                                  SchoolClassRepository schoolClassRepository){
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
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
            return "teacher/add";
        }
        return "redirect:/admin/teacher/" + teacherRepository.save(teacher).getId() + "/details";
    }
    @GetMapping("/{teacherId}/edit")
    public String edit(@PathVariable Long teacherId, Model model){
        model.addAttribute("teacher",teacherRepository.getReferenceById(teacherId));
        return "teacher/add";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        Teacher teacher = teacherRepository.getReferenceById(id);
        teacher.setActive(!teacher.isActive());
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/list";
    }
    @GetMapping("{id}/details")
    public String details(@PathVariable Long id, Model model,@SortDefault("id") Pageable pageable,
                          @QuerydslPredicate(root = Teacher.class) Predicate predicate){
        Teacher teacher = teacherRepository.getReferenceById(id);
        BooleanBuilder builder = new BooleanBuilder();
        QSchoolClass qSchoolClass = QSchoolClass.schoolClass;
        builder.and(qSchoolClass.supervisingTeachers.contains(teacher).not());
        builder.and(predicate);
        model.addAttribute("classes",schoolClassRepository.findAll(builder));
        model.addAttribute("teacher",teacher);
        model.addAttribute("freeUsers",userRepository.findAllByStudentIsNullAndTeacherIsNullAndParentIsNull());
        model.addAttribute("freeSubjects",subjectRepository.findAllWhichDontHaveTeacher(teacher));
        model.addAttribute("subjects",subjectRepository.findAllWhichHaveTeacher(teacher));
        return "teacher/admin/details";
    }
    @GetMapping("/{teacherId}/setUser")
    public String setUser(@PathVariable Long teacherId, @RequestParam Long user){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        if(teacher.getUser() == null) {
            User userObj = userRepository.getReferenceById(user);
            teacher.setUser(userObj);
            teacherRepository.save(teacher);
        }
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }
    @GetMapping("/{teacherId}/removeUser")
    public String clearUser(@PathVariable Long teacherId){
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        teacher.setUser(null);
        teacherRepository.save(teacher);
        return "redirect:/admin/teacher/" + teacherId +"/details";
    }
    @GetMapping("/{teacherId}/addSupervisedClass")
    public String addSupervisedClass(@PathVariable Long teacherId, @RequestParam Long schoolClassId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(schoolClassId);
        schoolClass.getSupervisingTeachers().add(teacherRepository.getReferenceById(teacherId));
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }
    @GetMapping("/{teacherId}/removeSupervisedClass")
    public String removeSupervisedClass(@PathVariable Long teacherId, @RequestParam Long schoolClassId){
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(schoolClassId);
        schoolClass.getSupervisingTeachers().remove(teacherRepository.getReferenceById(teacherId));
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }

    @GetMapping("{teacherId}/addSubject")
    public String addSubject(@PathVariable Long teacherId, @RequestParam Long subjectId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        subject.getTeachers().add(teacherRepository.getReferenceById(teacherId));
        subjectRepository.save(subject);
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }

    @GetMapping("{teacherId}/removeSubject")
    public String removeSubject(@PathVariable Long teacherId, @RequestParam Long subjectId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        subject.getTeachers().remove(teacherRepository.getReferenceById(teacherId));
        subjectRepository.save(subject);
        return "redirect:/admin/teacher/" + teacherId + "/details";
    }
}
