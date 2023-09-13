package pl.edziennik.edziennik.schoolClass;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.lessonHour.LessonHourRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlanRepository;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schoolclass")
public class SchoolClassController {
    private final SchoolClassRepository schoolClassRepository;
    private final LessonPlanRepository lessonPlanRepository;
    private final LessonHourRepository lessonHourRepository;
    public SchoolClassController(SchoolClassRepository schoolClassRepository,
                                 LessonPlanRepository lessonPlanRepository,
                                 LessonHourRepository lessonHourRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.lessonPlanRepository = lessonPlanRepository;
        this.lessonHourRepository = lessonHourRepository;
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
        model.addAttribute("hours",lessonHourRepository.findAllByOrderByStartAsc());
        return "schoolclass/lessonPlan";
    }
}
