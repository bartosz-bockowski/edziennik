package pl.edziennik.edziennik.lessonHour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonHourRepository extends JpaRepository<LessonHour, Long> {
    List<LessonHour> findAllByOrderByStartAsc();
}
