package pl.edziennik.edziennik.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edziennik.edziennik.security.LoggedUser;

@Controller
public class HomeController {
    private final LoggedUser loggedUser;

    public HomeController(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("loggedUser", loggedUser.getUser());
        return "main/home";
    }
}
