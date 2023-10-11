package pl.edziennik.edziennik.lessonPlan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/lessonplan")
public class LessonPlanAdminController {
    private final LessonPlanRepository lessonPlanRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final LessonHourRepository lessonHourRepository;

    public LessonPlanAdminController(LessonPlanRepository lessonPlanRepository,
                                     SubjectRepository subjectRepository,
                                     TeacherRepository teacherRepository,
                                     ClassRoomRepository classRoomRepository,
                                     SchoolClassRepository schoolClassRepository,
                                     LessonHourRepository lessonHourRepository) {
        this.lessonPlanRepository = lessonPlanRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.classRoomRepository = classRoomRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.lessonHourRepository = lessonHourRepository;
    }

    @GetMapping("/updateLesson")
    public String updateLesson(@RequestParam(required = false) Long classId,
                               @RequestParam(required = false) Long id,
                               @RequestParam(required = false) Long subject,
                               @RequestParam(required = false) Long teacher,
                               @RequestParam(required = false) Long classRoom,
                               @RequestParam(required = false) Long lessonHour,
                               @RequestParam(required = false) LocalDate date,
                               @RequestParam(required = false) String redirect) {
        LessonPlan lesson;
        if (id == null) {
            lesson = new LessonPlan();
        } else {
            lesson = lessonPlanRepository.getReferenceById(id);
        }
        lesson.setSubject(subjectRepository.getReferenceById(subject));
        lesson.setTeacher(teacherRepository.getReferenceById(teacher));
        lesson.setClassRoom(classRoomRepository.getReferenceById(classRoom));
        lesson.setSchoolClass(schoolClassRepository.getReferenceById(classId));
        lesson.setLessonHour(lessonHourRepository.getReferenceById(lessonHour));
        lesson.setDate(date);
        lessonPlanRepository.save(lesson);
        return "redirect:/schoolclass/" + lesson.getSchoolClass().getId() + "/lessonPlan?date=" + date;
    }
}
