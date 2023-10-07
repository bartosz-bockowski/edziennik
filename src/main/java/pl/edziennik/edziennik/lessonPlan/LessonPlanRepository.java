package pl.edziennik.edziennik.lessonPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.lessonPlan.LessonPlan;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {
    List<LessonPlan> getAllBySchoolClassIdAndDateIn(Long classId, List<LocalDate> dates);
    List<LessonPlan> getAllByTeacherIdAndDateIn(Long teacherId, List<LocalDate> dates);
}
