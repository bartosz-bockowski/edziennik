package pl.edziennik.edziennik.mark;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edziennik.edziennik.mark.category.MarkCategoryRepository;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.student.StudentRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/mark")
public class MarkController {
    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final MarkCategoryRepository markCategoryRepository;
    private final TeacherRepository teacherRepository;
    public MarkController(MarkRepository markRepository,
                          StudentRepository studentRepository,
                          MarkCategoryRepository markCategoryRepository,
                          TeacherRepository teacherRepository) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.markCategoryRepository = markCategoryRepository;
        this.teacherRepository = teacherRepository;
    }
    @ResponseBody
    @GetMapping("/add/{mark}/{studentId}/{markCategoryId}/{markId}")
    public ResponseEntity<Boolean> add(@PathVariable String mark, @PathVariable Long studentId, @PathVariable Long markCategoryId, @PathVariable String markId){
        Mark markObj = new Mark();
        Student student = studentRepository.getReferenceById(studentId);
        markObj.setMark(new BigDecimal(mark));
        markObj.setStudent(student);
        markObj.setMarkCategory(markCategoryRepository.getReferenceById(markCategoryId));
        markObj.setTime(LocalDateTime.now());
        markObj.setTeacher(teacherRepository.getReferenceById(1L));
        if(!markId.equals("null")){
            markObj.setId(Long.parseLong(markId));
        }
        markRepository.save(markObj);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
