package pl.edziennik.edziennik.security.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "{validation.error.uniqueValue}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
