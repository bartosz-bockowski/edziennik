package pl.edziennik.edziennik.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.edziennik.security.user.User;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("SELECT e FROM Parent e WHERE :user MEMBER OF e.users")
    List<Parent> findAllWhichHaveUser(User user);
}
