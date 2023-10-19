package pl.edziennik.edziennik.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> getAllBySchoolClassIdAndDateIn(Long classId, List<LocalDate> dates);

    List<Lesson> getAllBySchoolClassIdAndDateInAndActiveTrue(Long classId, List<LocalDate> dates);

    List<Lesson> getAllByTeacherIdAndDateIn(Long teacherId, List<LocalDate> dates);

    List<Lesson> getAllByTeacherIdAndDateInAndActiveTrue(Long teacherId, List<LocalDate> dates);
}
