package pl.edziennik.edziennik.schoolClass;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lesson.LessonRepository;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.student.StudentRepository;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/schoolclass")
public class SchoolClassAdminController {
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;
    private final LessonHourRepository lessonHourRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRoomRepository classRoomRepository;

    public SchoolClassAdminController(SchoolClassRepository schoolClassRepository,
                                      StudentRepository studentRepository,
                                      SubjectRepository subjectRepository,
                                      LessonRepository lessonRepository,
                                      LessonHourRepository lessonHourRepository,
                                      TeacherRepository teacherRepository,
                                      ClassRoomRepository classRoomRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.lessonRepository = lessonRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.teacherRepository = teacherRepository;
        this.classRoomRepository = classRoomRepository;
    }

    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable,
                       @QuerydslPredicate(root = SchoolClass.class) Predicate predicate) {
        QSchoolClass qSchoolClass = QSchoolClass.schoolClass;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qSchoolClass.active.eq(true));
        builder.and(predicate);
        model.addAttribute("page", schoolClassRepository.findAll(builder, pageable));
        return "schoolclass/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("schoolclass", new SchoolClass());
        return "schoolclass/add";
    }

    @PostMapping("/add")
    public String add(Model model, @Valid SchoolClass schoolClass, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            model.addAttribute("schoolclass", schoolClass);
            return "schoolclass/add";
        }
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/list";
    }

    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id) {
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(id);
        schoolClass.setActive(!schoolClass.isActive());
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/list";
    }

    @GetMapping("/{id}/adminDetails")
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("schoolClass", schoolClassRepository.getReferenceById(id));
        model.addAttribute("allStudents", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "schoolclass/adminDetails";
    }

    @GetMapping("/{id}/addStudent")
    public String addStudent(@PathVariable Long id, @RequestParam Long studentId) {
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(schoolClassRepository.getReferenceById(id));
        studentRepository.save(student);
        return "redirect:/admin/schoolclass/" + id + "/adminDetails";
    }

    @GetMapping("/{id}/removeStudent/{studentId}")
    public String removeStudent(@PathVariable Long id, @PathVariable Long studentId) {
        Student student = studentRepository.getReferenceById(studentId);
        student.setSchoolClass(null);
        studentRepository.save(student);
        return "redirect:/admin/schoolclass/" + id + "/adminDetails";
    }

    @GetMapping("/{id}/addSubject")
    public String addSubject(@PathVariable Long id, @RequestParam Long subjectId) {
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(id);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        if (!schoolClass.getSubjects().contains(subject)) {
            schoolClass.getSubjects().add(subject);
        }
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/" + id + "/adminDetails";
    }

    @GetMapping("{id}/removeSubject/{subjectId}")
    public String removeSubject(@PathVariable Long id, @PathVariable Long subjectId) {
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(id);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        schoolClass.getSubjects().remove(subject);
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/" + id + "/adminDetails";
    }

    @GetMapping("/updateLesson")
    public String updateLesson(@RequestParam(required = false) Long classId,
                               @RequestParam(required = false) Long id,
                               @RequestParam(required = false) Long subject,
                               @RequestParam(required = false) Long teacher,
                               @RequestParam(required = false) Long classRoom,
                               @RequestParam(required = false) Long lessonHour,
                               @RequestParam(required = false) LocalDate date) {
        Lesson lesson;
        if (id == null) {
            lesson = new Lesson();
        } else {
            lesson = lessonRepository.getReferenceById(id);
        }
        lesson.setSubject(subjectRepository.getReferenceById(subject));
        lesson.setTeacher(teacherRepository.getReferenceById(teacher));
        lesson.setClassRoom(classRoomRepository.getReferenceById(classRoom));
        lesson.setSchoolClass(schoolClassRepository.getReferenceById(classId));
        lesson.setLessonHour(lessonHourRepository.getReferenceById(lessonHour));
        lesson.setDate(date);
        lessonRepository.save(lesson);
        return "redirect:/admin/schoolclass/" + lesson.getSchoolClass().getId() + "/lessonPlan?date=" + date;
    }


    @GetMapping("/{classId}/setSupervisingTeacher/{teacherId}")
    public String setSupervisingTeacher(@PathVariable Long classId, @PathVariable Long teacherId) {
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(classId);
        schoolClass.getSupervisingTeachers().add(teacherRepository.getReferenceById(teacherId));
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/schoolclass/" + schoolClass.getId() + "/adminDetails";
    }
}
