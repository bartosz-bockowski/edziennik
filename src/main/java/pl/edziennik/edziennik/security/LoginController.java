package pl.edziennik.edziennik.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.security.failedLoginAttempt.FailedLoginAttemptService;
import pl.edziennik.edziennik.security.user.User;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model, HttpSession session, @RequestParam(required = false) Integer badPasswordCode){
        model.addAttribute("badPasswordCode",badPasswordCode);
        model.addAttribute("blockedUser",session.getAttribute("blockedUser"));
        User user = new User();
        model.addAttribute("user",user);
        model.addAttribute("testval","test");
        return "security/login";
    }
    @GetMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }
}
