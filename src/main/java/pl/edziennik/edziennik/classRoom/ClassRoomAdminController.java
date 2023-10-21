package pl.edziennik.edziennik.classRoom;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/classRoom")
public class ClassRoomAdminController {
    private final ClassRoomRepository classRoomRepository;

    public ClassRoomAdminController(ClassRoomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable) {
        model.addAttribute("page", classRoomRepository.findAll(pageable));
        return "classRoom/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("classRoom", new ClassRoom());
        return "classRoom/add";
    }

    @PostMapping("/add")
    public String add(Model model, @Valid ClassRoom classRoom, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("classRoom", classRoom);
            model.addAttribute("result", result);
            return "classRoom/add";
        }
        classRoomRepository.save(classRoom);
        return "redirect:/admin/classRoom/list";
    }
}
