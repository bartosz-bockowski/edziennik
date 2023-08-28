package pl.edziennik.edziennik.security.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/create-user")
    @ResponseBody
    public String createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        return "admin";
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
    public String add(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
        }
        userService.saveUser(user);
        return "redirect:/user/list";
    }
}
