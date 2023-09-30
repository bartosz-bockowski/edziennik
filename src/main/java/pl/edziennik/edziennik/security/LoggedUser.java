package pl.edziennik.edziennik.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;
import pl.edziennik.edziennik.student.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoggedUser {
    private final UserRepository userRepository;

    public LoggedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(){
        if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return null;
        }
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.orElse(null);
    }
    public Boolean hasAccessToSchoolClass(Long id){
        List<Student> students = getUser().getStudents();
        students.addAll(getUser().getParents().stream().flatMap(p -> p.getStudents().stream()).toList());
        boolean studentCondition = students.stream().map(Student::getSchoolClass).map(SchoolClass::getId).toList().contains(id);
        boolean teacherCondition = getUser().getTeachers().stream().flatMap(t -> t.getSupervisedClasses().stream()).map(SchoolClass::getId).toList().contains(id);
        boolean adminCondition = isAdmin();
        return studentCondition || teacherCondition || adminCondition;
    }
    public static Boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("admin"));
    }
}
