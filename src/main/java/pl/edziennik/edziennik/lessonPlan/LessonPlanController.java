package pl.edziennik.edziennik.lessonPlan;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.attendance.Attendance;
import pl.edziennik.edziennik.attendance.AttendanceRepository;
import pl.edziennik.edziennik.attendance.AttendanceType;
import pl.edziennik.edziennik.security.LoggedUser;

@Controller
@RequestMapping("/lessonplan")
public class LessonPlanController {
    private final LessonPlanRepository lessonPlanRepository;
    private final AttendanceRepository attendanceRepository;
    private final LoggedUser loggedUser;

    public LessonPlanController(LessonPlanRepository lessonPlanRepository,
                                AttendanceRepository attendanceRepository,
                                LoggedUser loggedUser) {
        this.lessonPlanRepository = lessonPlanRepository;
        this.attendanceRepository = attendanceRepository;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/{id}/create")
    public String create(@PathVariable Long id, Model model) {
        LessonPlan lessonPlan = lessonPlanRepository.getReferenceById(id);
        model.addAttribute("lessonPlan", lessonPlan);
        model.addAttribute("attendanceTypes", AttendanceType.values());
        return "lesson/create";
    }

    @PostMapping("/create")
    public String create(@Valid LessonPlan lessonPlan, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lessonPlan", lessonPlan);
            model.addAttribute("attendanceTypes", AttendanceType.values());
            return "lesson/create";
        }
        attendanceRepository.saveAll(lessonPlan.getAttendance());
        lessonPlan.setCompleted(true);
        lessonPlanRepository.save(lessonPlan);
        return "redirect:/teacher/" + loggedUser.getUser().getTeacher().getId() + "/lessonPlan?date=" + lessonPlan.getDashDate();
    }
}
