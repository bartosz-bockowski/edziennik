package pl.edziennik.edziennik.sampleClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleClassRepository extends JpaRepository<SampleClass, Long> {
}
