package pl.edziennik.edziennik.security.user;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edziennik.edziennik.utils.PasswordCriteria;
import pl.edziennik.edziennik.utils.SecurityUtils;

import jakarta.persistence.EntityManager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final EntityManager entityManager;

    public UserController(UserService userService,
                          MessageSource messageSource,
                          EntityManager entityManager) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.entityManager = entityManager;
    }

    @GetMapping("/{username}/account")
    public String account(@PathVariable String username, Model model){
        model.addAttribute("user",userService.findByUserName(username));
        model.addAttribute("passwordCriterias", PasswordCriteria.values());
        return "security/user/account";
    }
    @GetMapping("/{username}/changePassword")
    public ResponseEntity<List<String>> changePassword(@PathVariable String username, @RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmNewPassword){
        User user = userService.findByUserName(username);
        List<String> result = new ArrayList<>();
        if(!BCrypt.checkpw(currentPassword,user.getPassword())){
            result.add("wrongCurrentPassword");
        }
        if(!SecurityUtils.checkPassword(newPassword)){
            result.add("notFulfilled");
        }
        if(!newPassword.equals(confirmNewPassword)){
            result.add("NOT_CONFIRMED");
        }
        if(!checkLastFivePasswords(newPassword,user.getId())){
            result.add("foundInLastFivePasswords");
        }
        if(result.isEmpty()){
            user.setPassword(newPassword);
            userService.saveUser(user);
        }
        for(int i = 0; i < result.size(); i++){
            result.set(i,messageSource.getMessage("security.password.criteria." + result.get(i), null, LocaleContextHolder.getLocale()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public Boolean checkLastFivePasswords(String newPassword, Long userId){
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(User.class, false, false)
                .addOrder(AuditEntity.revisionNumber().desc())
                .setMaxResults(5);
        query.add(AuditEntity.id().eq(userId));
        List<Object[]> results = query.getResultList();
        for (Object[] result : results) {
            User user = (User) result[0];
            String password = user.getPassword();
            if(BCrypt.checkpw(newPassword,password)){
                return false;
            }
        }
        return true;
    }

    @GetMapping("/{username}/sendRestorePasswordEmail")
    public void sendRestorePasswordEmail(@PathVariable String username){
        User user = userService.findByUserName(username);
        if(user.getRestorePasswordCodeExp().isBefore(LocalDateTime.now())){
            user.setRestorePasswordCode(UUID.randomUUID().toString());
            user.setRestorePasswordCodeExp(LocalDateTime.now().plusMinutes(5));
            userService.saveUser(user);
        }
        String email = user.getEmail();
        String href = user.getUsername() + "/restorePassword/" + user.getRestorePasswordCode();
        //send email
    }

    @GetMapping("/{username}/restorePassword/{code}")
    public String restorePassword(@PathVariable String username, @PathVariable String code){
        User user = userService.findByUserName(username);
        if(user == null){
            return "redirect:/login";
        }
        if(user.getRestorePasswordCodeExp().isBefore(LocalDateTime.now()) || !user.getRestorePasswordCode().equals(code)){
            return "redirect:/login?badPasswordCode=1";
        }
        return "security/restorePassword";
    }
}
