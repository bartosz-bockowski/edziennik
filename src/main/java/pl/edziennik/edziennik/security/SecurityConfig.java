package pl.edziennik.edziennik.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/login")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/WEB-INF/views/security/login.jsp")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/img/**")).permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority("admin"))
                .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
//                .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())

                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .formLogin((form) -> form.loginPage("/login").failureUrl("/login?error=true").permitAll());
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}