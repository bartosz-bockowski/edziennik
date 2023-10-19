package pl.edziennik.edziennik.lesson;

import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.lessonHour.LessonHour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {
    public List<List<Lesson>> getPlan(List<LessonHour> hours, List<Lesson> lessons, LocalDate date) {
        List<List<Lesson>> plan = new ArrayList<>();
        for (LessonHour hour : hours) {
            List<Lesson> day = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                int finalJ = j;
                List<Lesson> l = lessons.stream().filter(lesson -> lesson.getDate().equals(date.plusDays(finalJ)) && lesson.getLessonHour().equals(hour)).toList();
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
