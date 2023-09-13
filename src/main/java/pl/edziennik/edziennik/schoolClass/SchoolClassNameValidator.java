package pl.edziennik.edziennik.schoolClass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
