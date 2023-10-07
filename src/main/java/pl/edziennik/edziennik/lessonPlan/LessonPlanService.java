package pl.edziennik.edziennik.lessonPlan;

import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.lessonHour.LessonHour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonPlanService {
    public List<List<LessonPlan>> getPlan(List<LessonHour> hours, List<LessonPlan> lessons, LocalDate date){
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
        return plan;
    }
}
