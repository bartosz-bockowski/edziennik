package pl.edziennik.edziennik.security.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.parameters.P;
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
import java.util.Random;
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
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",userRepository.findAll(pageable));
        return "security/user/list";
    }
    public String getNextId(){
        Long maxId = userRepository.getMaxId();
        String strMaxId = String.valueOf(maxId);
        int len = strMaxId.length();
        StringBuilder sb = new StringBuilder();
        if(len < 6){
            sb.append("0".repeat(6 - len));
        }
        sb.append(strMaxId);
        return sb.toString();
    }
    public String generatePassword(){
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        int len = AlphaNumericStr.length();
        StringBuilder passwordSB = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 20; i++){
            passwordSB.append(AlphaNumericStr.charAt(Math.abs(random.nextInt()) % len));
        }
        return passwordSB.toString();
    }
    @GetMapping("/add")
    public String add(){
        User user = new User();
        user.setUsername(getNextId());
        user.setPassword(generatePassword());
        System.out.println(user.getPassword());
        userService.saveUser(user);
        return "redirect:/admin/user/" + user.getId() + "/details";
    }
//    @PostMapping("/add")
//    public String add(@Valid User user, BindingResult result, Model model){
//        if(result.hasErrors()){
//            model.addAttribute("maxId", getNextId());
//            model.addAttribute("user",user);
//            model.addAttribute("result",result);
//            return "security/user/add";
//        }
//        userService.saveUser(user);
//        return "redirect:/admin/user/list";
//    }
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

    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        User user = userRepository.getOne(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
        return "redirect:/admin/user/list";
    }
}
