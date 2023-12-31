package pl.edziennik.edziennik.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.lesson.Lesson;
import pl.edziennik.edziennik.mark.Mark;
import pl.edziennik.edziennik.mark.category.MarkCategory;
import pl.edziennik.edziennik.parent.Parent;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoggedUser {
    private final UserRepository userRepository;

    public LoggedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return null;
        }
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.orElse(null);
    }

    public Boolean hasAccessToAnyStudentOfSchoolClass(Long id) {
        List<Student> students = new ArrayList<>();
        if (getUser().getParent() != null) {
            students.addAll(getUser().getParent().getStudents().stream().filter(f -> f.getSchoolClass() != null).toList());
        }
        if (getUser().getStudent() != null && getUser().getStudent().getSchoolClass() != null) {
            students.add(getUser().getStudent());
        }
        boolean studentCondition = !students.isEmpty() && students.stream().map(Student::getSchoolClass).map(SchoolClass::getId).toList().contains(id);
        boolean teacherCondition = getUser().getTeacher() != null && getUser().getTeacher().getSupervisedClasses().stream().map(SchoolClass::getId).toList().contains(id);
        return studentCondition || teacherCondition || isAdmin();
    }

    public Boolean hasAccessToStudent(Long id) {
        boolean studentCondition = getUser().getStudent() != null && getUser().getStudent().getId().equals(id);
        boolean parentCondition = getUser().getParent() != null && getUser().getParent().getStudents().stream().map(Student::getId).toList().contains(id);
        boolean teacherCondition = teacherSupervisesStudentsClass(id);
        return studentCondition || parentCondition || teacherCondition || isAdmin();
    }

    public Boolean hasAccessToStudentMarks(Long studentId, Long subjectId) {
        return hasAccessToStudent(studentId) || getUser().getTeacher() != null && getUser().getTeacher().getSubjects().stream().map(Subject::getId).toList().contains(subjectId);
    }

    public static Boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("admin"));
    }

    public Boolean hasAccessToTeacher(Long id) {
        return (getUser().getTeacher() != null && getUser().getTeacher().getId().equals(id)) || isAdmin();
    }

    public Boolean hasAccessToSchoolClassAdmin(Long id) {
        return (getUser().getTeacher() != null && getUser().getTeacher().getSupervisedClasses().stream().map(SchoolClass::getId).toList().contains(id)) || isAdmin();
    }

    public Boolean hasAccessToLesson(Long id) {
        Teacher teacher = getUser().getTeacher();
        return isAdmin() || teacher != null && teacher.getLessons().stream().map(Lesson::getId).toList().contains(id);
    }

    public Boolean hasSupervisedClasses() {
        return isAdmin() || getUser().getTeacher() != null && !getUser().getTeacher().getSupervisedClasses().isEmpty();
    }

    public Boolean teachesSubject(Long subjectId) {
        return isAdmin() || getUser().getTeacher() != null && getUser().getTeacher().getSubjects().stream().map(Subject::getId).toList().contains(subjectId);
    }

    public Boolean teachesSubjectOfMarkCategoryId(Long markCategoryId) {
        return isAdmin() || getUser().getTeacher() != null && getUser().getTeacher().getSubjects().stream().flatMap(subject -> subject.getMarkCategories().stream().map(MarkCategory::getId)).toList().contains(markCategoryId);
    }

    public Boolean hasAccessToMarkHistory(Long markId) {
        return isAdmin()
                ||
                getUser().getStudent() != null && getUser().getStudent().getMarks().stream().map(Mark::getId).toList().contains(markId)
                ||
                getUser().getParent() != null && getUser().getParent().getStudents().stream().flatMap(s -> s.getMarks().stream()).map(Mark::getId).toList().contains(markId)
                ||
                getUser().getTeacher() != null && getUser().getTeacher().getMarks().stream().map(Mark::getId).toList().contains(markId);
    }

    public Boolean isMarkCreator(Long markId) {
        return isAdmin() || getUser().getTeacher() != null && getUser().getTeacher().getMarks().stream().map(Mark::getId).toList().contains(markId);
    }

    public Boolean supervisesClass(Long schoolClassId) {
        return isAdmin() || getUser().getTeacher() != null && getUser().getTeacher().getSupervisedClasses().stream().map(SchoolClass::getId).toList().contains(schoolClassId);
    }

    public Boolean teacherSupervisesStudentsClass(Long studentId){
        return isAdmin() || getUser().getTeacher() != null && getUser().getTeacher().getSupervisedClasses().stream().flatMap(schoolClass -> schoolClass.getStudents().stream()).map(Student::getId).toList().contains(studentId);
    }

    public Boolean hasAccessToStudentsPersonalInfo(Long studentId){
        return isAdmin()
                ||
                getUser().getStudent() != null && getUser().getStudent().getId().equals(studentId)
                ||
                getUser().getParent() != null && getUser().getParent().getStudents().stream().map(Student::getId).toList().contains(studentId)
                ||
                getUser().getTeacher() != null && getUser().getTeacher().getSupervisedClasses().stream().flatMap(s -> s.getStudents().stream()).map(Student::getId).toList().contains(studentId);
    }
}
