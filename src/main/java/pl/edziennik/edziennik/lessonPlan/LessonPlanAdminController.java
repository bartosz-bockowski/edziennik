package pl.edziennik.edziennik.lessonPlan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.classRoom.ClassRoom;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.schoolClass.SchoolClassRepository;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.Teacher;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        ClassRoom classRoomObj = classRoomRepository.getReferenceById(classRoom);
        boolean classRoomFree = (id != null && classRoomObj.getId().equals(lesson.getClassRoom().getId())) || (id == null && classRoomObj.isFree(lessonHour1, date));
        lesson.setClassRoom(classRoomObj);

        lesson.setClassRoom(classRoomRepository.getReferenceById(classRoom));
        lesson.setSchoolClass(schoolClassRepository.getReferenceById(classId));
        lesson.setLessonHour(lessonHour1);
        lesson.setDate(date);
        StringBuilder params = new StringBuilder();
        if (teacherFree && classRoomFree) {
            lessonPlanRepository.save(lesson);
        }
        return "redirect:/schoolclass/" + lesson.getSchoolClass().getId() + "/lessonPlan?date=" + date;
    }

    @GetMapping("/validateLesson")
    public ResponseEntity<List<Integer>> validateLesson(@RequestParam(required = false) Long id,
                                                        @RequestParam Long teacher,
                                                        @RequestParam Long classRoom,
                                                        @RequestParam Long subject,
                                                        @RequestParam Long lessonHour,
                                                        @RequestParam LocalDate date) {
        LessonPlan lesson;
        if (id == null) {
            lesson = new LessonPlan();
        } else {
            lesson = lessonPlanRepository.getReferenceById(id);
        }
        LessonHour lessonHour1 = lessonHourRepository.getReferenceById(lessonHour);

        Teacher teacherObj = teacherRepository.getReferenceById(teacher);
        boolean teacherChange = id != null && !teacherObj.getId().equals(lesson.getTeacher().getId());
        boolean teacherFree = (id != null && teacherObj.getId().equals(lesson.getTeacher().getId())) || (id == null && teacherObj.isFree(lessonHour1, date));

        ClassRoom classRoomObj = classRoomRepository.getReferenceById(classRoom);
        boolean classRoomChange = id != null && !classRoomObj.getId().equals(lesson.getClassRoom().getId());
        boolean classRoomFree = (id != null && classRoomObj.getId().equals(lesson.getClassRoom().getId())) || (id == null && classRoomObj.isFree(lessonHour1, date));

        Subject subjectObj = subjectRepository.getReferenceById(subject);
        boolean subjectChange = id != null && !subjectObj.getId().equals(lesson.getSubject().getId());

        List<Integer> response = new ArrayList<>();
        if (id != null && !teacherChange && !classRoomChange && !subjectChange) {
            response.add(0);
        } else if (teacherFree && classRoomFree) {
            response.add(1);
        } else {
            if (!teacherFree) {
                response.add(2);
            }
            if (!classRoomFree) {
                response.add(3);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
