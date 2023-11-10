package pl.edziennik.edziennik.student;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;

@Controller
@RequestMapping("/admin/student")
public class StudentAdminController {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    public StudentAdminController(StudentRepository studentRepository,
                                  UserRepository userRepository,
                                  SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable) {
        model.addAttribute("page", studentRepository.findAll(pageable));
        return "student/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id) {
        Student student = studentRepository.getReferenceById(id);
        student.setActive(!student.isActive());
        studentRepository.save(student);
        return "redirect:/admin/student/list";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) {
        Student student = studentRepository.getReferenceById(id);
        model.addAttribute("student", student);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("freeUsers", userRepository.findAllByStudentIsNullAndTeacherIsNullAndParentIsNull());
        model.addAttribute("schoolClasses", schoolClassRepository.findAll());
        return "student/admin/details";
    }

    @GetMapping("/{studentId}/setUser")
    public String setUser(@PathVariable Long studentId, @RequestParam Long user) {
        Student student = studentRepository.getReferenceById(studentId);
        if (student.getUser() == null) {
            User userObj = userRepository.getReferenceById(user);
            student.setUser(userObj);
            studentRepository.save(student);
        }
        return "redirect:/admin/student/" + studentId + "/details";
    }

    @GetMapping("/{studentId}/removeUser")
    public String clearUser(@PathVariable Long studentId) {
        Student student = studentRepository.getReferenceById(studentId);
        student.setUser(null);
        studentRepository.save(student);
        return "redirect:/admin/student/" + studentId + "/details";
    }

    @GetMapping("/{studentId}/setClass")
    public String setClass(@PathVariable Long studentId, @RequestParam Long classId) {
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(schoolClassRepository.getReferenceById(classId));
        studentRepository.save(student);
        return "redirect:/admin/student/" + student.getId() + "/details";
    }
}