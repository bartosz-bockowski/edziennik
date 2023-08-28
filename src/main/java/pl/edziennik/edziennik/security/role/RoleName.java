package pl.edziennik.edziennik.security.role;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.edziennik.edziennik.security.user.UsernameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleNameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleName {
    String message() default "{validation.error.uniqueValue}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
