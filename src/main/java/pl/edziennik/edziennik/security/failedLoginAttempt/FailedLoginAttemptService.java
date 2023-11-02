package pl.edziennik.edziennik.security.failedLoginAttempt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FailedLoginAttemptService {
    private final FailedLoginAttemptRepository failedLoginAttemptRepository;
    private final UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    public FailedLoginAttemptService(FailedLoginAttemptRepository failedLoginAttemptRepository, UserService userService) {
        this.failedLoginAttemptRepository = failedLoginAttemptRepository;
        this.userService = userService;
    }
    public void failedAttemptForUser(User user){
        FailedLoginAttempt failedLoginAttempt = new FailedLoginAttempt();
        failedLoginAttempt.setUser(user);
        failedLoginAttempt.setTime(LocalDateTime.now());
        failedLoginAttempt.setIp(request.getRemoteAddr());
        failedLoginAttemptRepository.save(failedLoginAttempt);
    }
    public void failedAttemptForUser(String username){
        failedAttemptForUser(userService.findByUserName(username));
    }
    public Boolean isUserBlocked(User user){
        return failedLoginAttemptRepository.countAllByUserAndIpAndTimeAfter(user, request.getRemoteAddr(), LocalDateTime.now().minusMinutes(5)) >= 5;
    }
    public Boolean isUserBlocked(String username){
        return isUserBlocked(userService.findByUserName(username));
    }
    public void clearHistory(User user){
        failedLoginAttemptRepository.deleteAllByUserAndIp(user,request.getRemoteAddr());
    }
    public void clearHistory(String username){
        clearHistory(userService.findByUserName(username));
    }
}
