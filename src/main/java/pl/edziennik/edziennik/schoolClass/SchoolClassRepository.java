package pl.edziennik.edziennik.schoolClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByName(String name);
    Optional<SchoolClass> findByNameAndIdNot(String name, Long id);
}
