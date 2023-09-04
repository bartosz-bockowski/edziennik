package pl.edziennik.edziennik.security.role;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleRepository roleRepository;
    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @SortDefault("id") Pageable pageable){
        model.addAttribute("page",roleRepository.findAll(pageable));
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
            model.addAttribute("result",result);
            return "security/role/add";
        }
        role.setActive(true);
        roleRepository.save(role);
        return "redirect:/admin/role/list";
    }
    @GetMapping("/{id}/switch")
    public String switch_(@PathVariable Long id){
        Role role = roleRepository.getOne(id);
        role.setActive(!role.isActive());
        roleRepository.save(role);
        return "redirect:/admin/role/list";
    }
}
