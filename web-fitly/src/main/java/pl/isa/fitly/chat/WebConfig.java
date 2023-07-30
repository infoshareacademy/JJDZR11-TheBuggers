package pl.isa.fitly.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.isa.fitly.controller.ContactsController;
import pl.isa.fitly.repository.UserRepository;

@Configuration
@ComponentScan(basePackages = "pl.isa.fitly.controller")
public class WebConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    public WebConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/contacts").setViewName("contacts");
    }
}


