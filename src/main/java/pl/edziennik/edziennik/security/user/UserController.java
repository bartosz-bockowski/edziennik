package pl.edziennik.edziennik.security.user;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.edziennik.security.LoggedUser;
import pl.edziennik.edziennik.utils.PasswordCriteria;
import pl.edziennik.edziennik.utils.SecurityUtils;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final EntityManager entityManager;
    private final LoggedUser loggedUser;

    public UserController(UserService userService,
                          MessageSource messageSource,
                          EntityManager entityManager,
                          LoggedUser loggedUser) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.entityManager = entityManager;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/{username}/account")
    public String account(@PathVariable String username, Model model){
        model.addAttribute("user",userService.findByUserName(username));
        model.addAttribute("passwordCriterias", PasswordCriteria.values());
        return "security/user/account";
    }
    @GetMapping("/{username}/changePassword")
    public ResponseEntity<List<String>> changePassword(@PathVariable String username, @RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmNewPassword){
        if(!username.equals(loggedUser.getUser().getUsername())){
            return null;
        }
        User user = userService.findByUserName(username);
        List<String> result = new ArrayList<>();
        if(!BCrypt.checkpw(currentPassword,user.getPassword())){
            result.add("wrongCurrentPassword");
        }
        result.addAll(checkPassword(newPassword,confirmNewPassword, user.getId()));
        if(result.isEmpty()){
            user.setPassword(newPassword);
            userService.saveUser(user);
        }
        result.replaceAll(s -> messageSource.getMessage("security.password.criteria." + s, null, LocaleContextHolder.getLocale()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public List<String> checkPassword(String newPassword, String confirmNewPassword, Long id){
        List<String> result = new ArrayList<>();
        if(!SecurityUtils.checkPassword(newPassword)){
            result.add("notFulfilled");
        }
        if(!newPassword.equals(confirmNewPassword)){
            result.add("NOT_CONFIRMED");
        }
        if(!checkLastFivePasswords(newPassword,id)){
            result.add("foundInLastFivePasswords");
        }
        return result;
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
            String email = user.getEmail();
            String href = "restorePassword/" + user.getRestorePasswordCode();
            //send email
        }
    }

    @GetMapping("/restorePassword/{code}")
    public String restorePassword(@PathVariable String code, Model model){
        User user = userService.findByRestorePasswordCode(code);
        if(user == null || user.getRestorePasswordCodeExp().isBefore(LocalDateTime.now()) || !user.getRestorePasswordCode().equals(code)){
            return "redirect:/login?badPasswordCode=1";
        }
        model.addAttribute("passwordCriterias",PasswordCriteria.values());
        model.addAttribute("user",user);
        model.addAttribute("restorePasswordCode",code);
        return "security/restorePassword";
    }

    @GetMapping("/{code}/restorePassword")
    public ResponseEntity<List<String>> changePassword(@PathVariable String code, @RequestParam String newPassword, @RequestParam String confirmNewPassword){
        User user = userService.findByRestorePasswordCode(code);
        List<String> result = new ArrayList<>();
        HttpStatus responseStatus = HttpStatus.OK;
        if(user == null || user.getRestorePasswordCodeExp().isBefore(LocalDateTime.now())){
            result.add("badPasswordCode.form");
            responseStatus = HttpStatus.BAD_REQUEST;
        } else {
            result.addAll(checkPassword(newPassword, confirmNewPassword, user.getId()));
        }
        if(result.isEmpty()){
            user.setPassword(newPassword);
            userService.saveUser(user);
        }
        result.replaceAll(s -> messageSource.getMessage("security.password.criteria." + s, null, LocaleContextHolder.getLocale()));
        return new ResponseEntity<>(result, responseStatus);
    }
}
