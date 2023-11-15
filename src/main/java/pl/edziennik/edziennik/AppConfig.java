package pl.edziennik.edziennik;

        import com.querydsl.core.types.dsl.EntityPathBase;
        import com.querydsl.core.types.dsl.StringPath;
        import org.springframework.context.MessageSource;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.annotation.Description;
        import org.springframework.context.support.ReloadableResourceBundleMessageSource;
        import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
        import org.springframework.data.querydsl.binding.QuerydslBindings;
        import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
        import org.thymeleaf.spring6.SpringTemplateEngine;
        import org.thymeleaf.spring6.view.ThymeleafViewResolver;
        import org.thymeleaf.templatemode.TemplateMode;
        import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
        import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
        import jakarta.servlet.ServletContext.*;
        import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class AppConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
