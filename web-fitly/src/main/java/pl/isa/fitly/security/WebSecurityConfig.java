package pl.isa.fitly.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    UserRepository userRepository;
    private final String[] UNAUTHORIZED_DOMAINS = {"/", "/home", "/bmi", "/trainings", "/diets", "/register"};


    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(UNAUTHORIZED_DOMAINS).permitAll()
//                        .requestMatchers(HttpMethod.POST,"/").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> {
                    logout.permitAll().logoutSuccessUrl("/");
                    userRepository.setCurrentUser(UserData.createUserData());
                });
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        for (UserData userData : userRepository.getUsersData()) {
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username(userData.getEmail())
                    .password(userData.getPassword())
                    .roles("USER")
                    .build();
            inMemoryUserDetailsManager.createUser(user);
        }
        return inMemoryUserDetailsManager;
    }


}
