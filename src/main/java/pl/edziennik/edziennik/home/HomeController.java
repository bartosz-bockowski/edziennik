package pl.edziennik.edziennik.home;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUT: " + authentication);
        model.addAttribute("name",authentication.getName());
        return "test/home";
    }
}
