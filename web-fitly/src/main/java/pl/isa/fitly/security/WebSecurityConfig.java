package pl.isa.fitly.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	UserRepository userRepository;


	public WebSecurityConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home","/diets","/bmi","/register","/m").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}


	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		for(UserData userData :userRepository.getUsersData()){
			UserDetails user =
					User.withDefaultPasswordEncoder()
							.username(userData.getEmail())
							.password(userData.getPassword())
							.roles("USER")
							.build();
			inMemoryUserDetailsManager.createUser(user);
		}

		return inMemoryUserDetailsManager;
	}




}
