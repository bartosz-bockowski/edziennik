package pl.edziennik.edziennik.security.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, User> {
    private final UserRepository userRepository;
    public UsernameValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public void initialize(Username username) {
    }
    @Override
    public boolean isValid(User value, ConstraintValidatorContext context){
        boolean result = true;
        if(value.getId() == null && value.getUsername() != null && !userRepository.findByUsername(value.getUsername()).isEmpty()){
            result = false;
        }
        if(value.getId() != null && value.getUsername() != null && !userRepository.findByUsernameAndIdNot(value.getUsername(), value.getId()).isEmpty()){
            result = false;
        }
        if(!result){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("username").addConstraintViolation();
        }
        return result;
    }
}
