package pl.edziennik.edziennik.schoolClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long>, QuerydslPredicateExecutor<SchoolClass> {
    Optional<SchoolClass> findByName(String name);
    Optional<SchoolClass> findByNameAndIdNot(String name, Long id);
}
