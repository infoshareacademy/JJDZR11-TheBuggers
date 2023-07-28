package pl.isa.fitly.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.isa.fitly.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)

public class WebSecurityConfig {
    UserRepository userRepository;
    private final String[] UNAUTHORIZED_DOMAINS = {"/", "/home", "/bmi", "/trainings", "/training", "/diets", "/register",
           "/diets/*", "/css/*.css", "/MacroAndMicroNutrients", "MacroAndMicroNutrients/search" ,"/img/**", "/trainings/**"};


    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request
                        .requestMatchers(UNAUTHORIZED_DOMAINS).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .failureUrl("/login")
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout.permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout=true"))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
