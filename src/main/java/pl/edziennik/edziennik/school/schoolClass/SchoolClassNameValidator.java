package pl.edziennik.edziennik.school.schoolClass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.edziennik.edziennik.security.user.User;
import pl.edziennik.edziennik.security.user.UserRepository;
import pl.edziennik.edziennik.security.user.Username;

public class SchoolClassNameValidator implements ConstraintValidator<SchoolClassName, SchoolClass> {
    private final SchoolClassRepository schoolClassRepository;
    public SchoolClassNameValidator(SchoolClassRepository schoolClassRepository){
        this.schoolClassRepository = schoolClassRepository;
    }
    @Override
    public void initialize(SchoolClassName schoolClassName) {
    }
    @Override
    public boolean isValid(SchoolClass value, ConstraintValidatorContext context){
        boolean result = true;
        if(value.getId() == null && value.getName() != null && !schoolClassRepository.findByName(value.getName()).isEmpty()){
            result = false;
        }
        if(value.getId() != null && value.getName() != null && !schoolClassRepository.findByNameAndIdNot(value.getName(), value.getId()).isEmpty()){
            result = false;
        }
        if(!result){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("name").addConstraintViolation();
        }
        return result;
    }
}
