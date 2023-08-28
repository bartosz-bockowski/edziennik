package pl.edziennik.edziennik.security.role;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final RoleRepository roleRepository;
    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("roles",roleRepository.findAll());
        return "security/role/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        Role role = new Role();
        model.addAttribute("role",role);
        return "security/role/add";
    }
    @PostMapping("/add")
    public String add(@Valid Role role, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("role",role);
            return "security/role/add";
        }
        roleRepository.save(role);
        return "redirect:/role/list";
    }
}
