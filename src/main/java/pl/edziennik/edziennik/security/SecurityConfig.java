package pl.edziennik.edziennik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> {
//                    auth.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyAuthority("admin");
//                    auth.requestMatchers(new AntPathRequestMatcher("/img/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/WEB-INF/views/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/login")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/static/ico/favicon.ico")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/user/**/sendRestorePasswordEmail")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/user/restorePassword/**")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/user/**/restorePassword")).permitAll();
//                    auth.requestMatchers(new AntPathRequestMatcher("/user/createAdmin")).permitAll();
//                    auth.anyRequest().authenticated();
                    auth.anyRequest().permitAll();
                })
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .formLogin((form) -> form.loginPage("/login").failureUrl("/login?error=true").permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}