package pl.edziennik.edziennik.lessonPlan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.classRoom.ClassRoom;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.Teacher;
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
                               @RequestParam(required = false) LocalDate date) {
        LessonPlan lesson;
        if (id == null) {
            lesson = new LessonPlan();
        } else {
            lesson = lessonPlanRepository.getReferenceById(id);
        }
        LessonHour lessonHour1 = lessonHourRepository.getReferenceById(lessonHour);
        lesson.setSubject(subjectRepository.getReferenceById(subject));

        Teacher teacherObj = teacherRepository.getReferenceById(teacher);
        boolean teacherFree = (id != null && teacherObj.getId().equals(lesson.getTeacher().getId())) || (id == null && teacherObj.isFree(lessonHour1, date));
        lesson.setTeacher(teacherObj);

        ClassRoom classRommObj = classRoomRepository.getReferenceById(classRoom);
        boolean classRoomFree = (id != null && classRommObj.getId().equals(lesson.getClassRoom().getId())) || (id == null && classRommObj.isFree(lessonHour1, date));
        lesson.setClassRoom(classRommObj);

        lesson.setClassRoom(classRoomRepository.getReferenceById(classRoom));
        lesson.setSchoolClass(schoolClassRepository.getReferenceById(classId));
        lesson.setLessonHour(lessonHour1);
        lesson.setDate(date);
        StringBuilder params = new StringBuilder();
        if (teacherFree && classRoomFree) {
            lessonPlanRepository.save(lesson);
        }
        if (!teacherFree) {
            params.append("&teacherNotFree=1");
        }
        if (!classRoomFree) {
            params.append("&classRoomNotFree=1");
        }
        return "redirect:/schoolclass/" + lesson.getSchoolClass().getId() + "/lessonPlan?date=" + date + params;
    }
}
