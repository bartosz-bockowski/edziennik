package pl.edziennik.edziennik.schoolClass;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SchoolClassNameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SchoolClassName {
    String message() default "{validation.error.uniqueValue}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
