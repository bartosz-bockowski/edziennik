package pl.edziennik.edziennik.security.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.school.subject.Subject;
import pl.edziennik.edziennik.school.teacher.Teacher;
import pl.edziennik.edziennik.security.role.RoleRepository;

import jakarta.servlet.http.HttpServletRequest;

import javax.xml.stream.StreamFilter;
import java.util.List;
import java.util.stream.Stream;

@RequestMapping("/admin/user")
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserService userService,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "security/user/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "security/user/add";
    }
    @PostMapping("/add")
    public String add(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("user",user);
            model.addAttribute("result",result);
            return "security/user/add";
        }
        userService.saveUser(user);
        return "redirect:/admin/user/list";
    }
    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model){
        User user = userRepository.getOne(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",roleRepository.findAll());
        return "security/user/details";
    }

    @GetMapping("/{id}/setRoles")
    public String setRoles(@PathVariable Long id, @RequestParam("roles") String roles){
        User user = userRepository.getOne(id);
        for(String x : roles.split(",")){
            user.getRoles().add(roleRepository.getOne(Long.parseLong(x)));
        }
        userService.saveUser(user);
        return "redirect:/admin/user/" + id + "/details";
    }
}
