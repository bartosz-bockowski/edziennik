package pl.edziennik.edziennik.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Mark getMarkByStudentIdAndMarkCategoryId(Long studentId, Long markCategoryId);
}
