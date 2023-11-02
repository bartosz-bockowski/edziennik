package pl.edziennik.edziennik.security.loginListener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import pl.edziennik.edziennik.security.failedLoginAttempt.FailedLoginAttemptService;
import pl.edziennik.edziennik.security.user.UserServiceImpl;

import java.time.LocalDateTime;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private FailedLoginAttemptService failedLoginAttemptService;
    @Autowired
    private HttpSession session;

    public AuthenticationFailureListener(FailedLoginAttemptService failedLoginAttemptService) {
        this.failedLoginAttemptService = failedLoginAttemptService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e){
        String username = e.getAuthentication().getName();
        if(!failedLoginAttemptService.isUserBlocked(username)){
            failedLoginAttemptService.failedAttemptForUser(username);
        }
        if(failedLoginAttemptService.isUserBlocked(username)){
            session.setAttribute("blockedUser",true);
        }
    }
}
