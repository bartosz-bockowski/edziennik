package pl.edziennik.edziennik.lessonPlan;

import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.lessonHour.LessonHour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonPlanService {
    public List<List<LessonPlan>> getPlan(List<LessonHour> hours, List<LessonPlan> lessons, LocalDate date) {
        List<List<LessonPlan>> plan = new ArrayList<>();
        for (LessonHour hour : hours) {
            List<LessonPlan> day = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                int finalJ = j;
                List<LessonPlan> l = lessons.stream().filter(lesson -> lesson.getDate().equals(date.plusDays(finalJ)) && lesson.getLessonHour().equals(hour)).toList();
                if (l.isEmpty()) {
                    day.add(null);
                } else {
                    day.add(l.get(0));
                }
            }
            plan.add(day);
        }
        return plan;
    }
}
