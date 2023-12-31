package pl.edziennik.edziennik.security.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.parent.ParentRepository;
import pl.edziennik.edziennik.student.StudentRepository;
import pl.edziennik.edziennik.teacher.TeacherRepository;
import pl.edziennik.edziennik.security.role.RoleRepository;
import pl.edziennik.edziennik.security.role.Role;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/admin/user")
@Controller
public class UserAdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserAdminController(UserService userService,
                               UserRepository userRepository,
                               RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/list")
    public String list(Model model, @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @QuerydslPredicate(root = User.class)Predicate predicate) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(predicate);
        model.addAttribute("page", userRepository.findAll(builder, pageable));
        return "security/user/list";
    }

    public String getNextId() {
        Long maxId = userRepository.getMaxId();
        String strMaxId = String.valueOf(maxId);
        int len = strMaxId.length();
        StringBuilder sb = new StringBuilder();
        if (len < 6) {
            sb.append("0".repeat(6 - len));
        }
        sb.append(strMaxId);
        return sb.toString();
    }

    @GetMapping("/add")
    public String add() {
        User user = new User();
        user.setUsername(getNextId());
        user.setPassword("123");
        userService.saveUser(user);
        return "redirect:/admin/user/" + user.getId() + "/details";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) {
        User user = userRepository.getReferenceById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "security/user/details";
    }

    @GetMapping("/{id}/setRoles")
    public String setRoles(@PathVariable Long id, @RequestParam(value = "roles", required = false) String roles) {
        User user = userRepository.getReferenceById(id);
        Set<Role> roleList = user.getRoles();
        Set<Role> result = new HashSet<>();
        if (roles == null) {
            for (Role role : roleList) {
                roleList.remove(role);
            }
        } else {
            for (String x : roles.split(",")) {
                result.add(roleRepository.getReferenceById(Long.parseLong(x)));
            }
        }
        user.setRoles(result);
        userRepository.save(user);
        return "redirect:/admin/user/" + id + "/details";
    }

    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id) {
        User user = userRepository.getReferenceById(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
        return "redirect:/admin/user/list";
    }
}
