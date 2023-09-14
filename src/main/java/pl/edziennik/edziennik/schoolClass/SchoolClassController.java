package pl.edziennik.edziennik.schoolClass;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.classRoom.ClassRoomRepository;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlanRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;
import pl.edziennik.edziennik.subject.SubjectRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schoolclass")
public class SchoolClassController {
    private final SchoolClassRepository schoolClassRepository;
    private final LessonPlanRepository lessonPlanRepository;
    private final LessonHourRepository lessonHourRepository;
    private final ClassRoomRepository classRoomRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    public SchoolClassController(SchoolClassRepository schoolClassRepository,
                                 LessonPlanRepository lessonPlanRepository,
                                 LessonHourRepository lessonHourRepository,
                                 ClassRoomRepository classRoomRepository,
                                 TeacherRepository teacherRepository,
                                 SubjectRepository subjectRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.lessonPlanRepository = lessonPlanRepository;
        this.lessonHourRepository = lessonHourRepository;
        this.classRoomRepository = classRoomRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/{classId}/lessonPlan")
    public String lessonPlan(Model model, @RequestParam(value = "date", required = false) LocalDate date, @PathVariable Long classId){
        model.addAttribute("schoolClass",schoolClassRepository.getReferenceById(classId));
        if(date == null){
            date = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        }
        List<LocalDate> dates = new ArrayList<>();
        dates.add(date);
        for(int i = 1; i < 5; i++){
            dates.add(date.plusDays(i));
        }
        List<LessonPlan> lessons = lessonPlanRepository.getAllBySchoolClassIdAndDateIn(classId, dates);
        model.addAttribute("lessons",lessons);
        List<LessonHour> hours = lessonHourRepository.findAllByActiveTrueOrderByStartAsc();
        model.addAttribute("hours",hours);
        List<List<LessonPlan>> plan = new ArrayList<>();
        for(LessonHour hour : hours){
            List<LessonPlan> day = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                boolean flag = true;
                for(LessonPlan lesson : lessons){
                    if(lesson.getDate().equals(date.plusDays(j)) && lesson.getLessonHour().equals(hour)){
                        flag = false;
                        day.add(lesson);
                        break;
                    }
                }
                if(flag){
                    day.add(null);
                }
            }
            plan.add(day);
        }
        model.addAttribute("plan",plan);
        model.addAttribute("hours",hours);
        model.addAttribute("date",date);

        //if admin
        model.addAttribute("subjects",subjectRepository.findAll());
        model.addAttribute("teachers",teacherRepository.findAll());
        model.addAttribute("classRooms",classRoomRepository.findAll());
        return "schoolclass/lessonPlanAdmin";
        //koniec if admin
        //return "schoolclass/lessonPlan";
    }
}
