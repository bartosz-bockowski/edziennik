package pl.edziennik.edziennik.lessonPlan;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import pl.edziennik.edziennik.attendance.Attendance;
import pl.edziennik.edziennik.classRoom.ClassRoom;
import pl.edziennik.edziennik.exam.Exam;
import pl.edziennik.edziennik.lessonHour.LessonHour;
import pl.edziennik.edziennik.schoolClass.SchoolClass;
import pl.edziennik.edziennik.student.Student;
import pl.edziennik.edziennik.subject.Subject;
import pl.edziennik.edziennik.teacher.Teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class LessonPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Teacher createdBy;
    @ManyToOne
    private LessonHour lessonHour;
    @ManyToOne
    private ClassRoom classRoom;
    private LocalDate date;
    @ManyToOne
    private SchoolClass schoolClass;
    @ManyToOne
    private Subject subject;
    @OneToMany(mappedBy = "lesson")
    private List<Exam> exams;
    @NotEmpty
    @Length(min = 3)
    private String topic;
    private Boolean completed = false;
    @OneToMany(mappedBy = "lessonPlan")
    private List<Attendance> attendance;
    private Boolean active = true;

    public Boolean hasActiveExams() {
        return !this.exams.stream().filter(Exam::getActive).toList().isEmpty();
    }

    public Exam getLastExam() {
        return this.exams.get(this.exams.size() - 1);
    }

    public String getFormattedDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getDashDate() {
        return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Long getAttendanceIdByStudentId(Long studentId) {
        List<Long> result = this.attendance.stream().filter(f -> f.getStudent() != null).filter(f -> f.getStudent().getId().equals(studentId)).map(Attendance::getId).toList();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.date, this.lessonHour.getStart());
    }
}
