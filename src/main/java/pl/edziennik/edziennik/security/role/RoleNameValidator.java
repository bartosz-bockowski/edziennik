package pl.edziennik.edziennik.security.role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleNameValidator implements ConstraintValidator<RoleName, Role> {
    private final RoleRepository roleRepository;
    public RoleNameValidator(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public void initialize(RoleName roleName) {
    }
    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context){
        System.out.println("ID: " + value.getId());
        System.out.println("NAZWA: " + value.getName());
        boolean result = true;
        if(value.getId() == null &&
                value.getName() != null &&
                !roleRepository.findByName(value.getName()).isEmpty()){
            result = false;
        }
        if(value.getId() != null &&
                value.getName() != null &&
                !roleRepository.findByNameAndIdNot(value.getName(), value.getId()).isEmpty()){
            result = false;
        }
        if(!result){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("name").addConstraintViolation();
        }
        System.out.println(result);
        return result;
    }
}

