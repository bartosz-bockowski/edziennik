package pl.edziennik.edziennik.security.loginListener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.edziennik.edziennik.security.failedLoginAttempt.FailedLoginAttemptService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private FailedLoginAttemptService failedLoginAttemptService;
    @Autowired
    private HttpSession session;

    public AuthenticationSuccessListener(FailedLoginAttemptService failedLoginAttemptService) {
        this.failedLoginAttemptService = failedLoginAttemptService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e){
        String username = e.getAuthentication().getName();
        if(failedLoginAttemptService.isUserBlocked(username)){
            session.setAttribute("blockedUser",true);
            throw new BadCredentialsException("");
        } else {
            failedLoginAttemptService.clearHistory(username);
        }
    }
}
