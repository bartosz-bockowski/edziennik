package pl.edziennik.edziennik;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebConfiguration {
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
//    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
}
