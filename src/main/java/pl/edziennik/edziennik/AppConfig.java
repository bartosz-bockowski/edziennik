package pl.edziennik.edziennik;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import pl.edziennik.edziennik.utils.PasswordCriteria;

@Configuration
@WebListener
public class AppConfig implements ServletContextListener {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("passwordCriterias", PasswordCriteria.values());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP
    }
}
