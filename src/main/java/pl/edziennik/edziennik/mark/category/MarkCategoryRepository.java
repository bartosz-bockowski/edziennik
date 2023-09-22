package pl.edziennik.edziennik.mark.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkCategoryRepository extends JpaRepository<MarkCategory, Long> {
    List<MarkCategory> findAllBySchoolClassIdAndSubjectId(Long classId, Long subjectId);
}
