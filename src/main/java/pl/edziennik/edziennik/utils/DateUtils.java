package pl.edziennik.edziennik.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public LocalDate getDateAndListOfDatesForLessonPlan(LocalDate date, List<LocalDate> dates) {
        if (date == null) {
            date = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        } else {
            date = date.minusDays(date.getDayOfWeek().getValue() - 1);
        }
        dates.add(date);
        for (int i = 1; i < 5; i++) {
            dates.add(date.plusDays(i));
        }
        return date;
    }

    public List<LocalDate> lastFifteenDays(LocalDate... dates) {
        LocalDate date;
        if (dates.length == 0) {
            date = LocalDate.now();
        } else {
            date = dates[0];
        }
        List<LocalDate> result = new ArrayList<>();
        result.add(date);
        for (int i = 1; i < 15; i++) {
            result.add(date.minusDays(i));
        }
        return result;
    }
}
