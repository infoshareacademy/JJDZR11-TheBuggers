package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;

@Controller
public class LoginController {
    UserData userData;
    UserRepository userRepository;

    public LoginController(UserData userData, UserRepository userRepository) {
        this.userData = userData;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String userSite(Model model) {
        if (userRepository.getCurrentUser() != null && !userRepository.getCurrentUser().emptyUser()) {
            model.addAttribute("userName", "Current user: " + userRepository.getCurrentUser().getEmail());
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout(UserData userData, Model model) {
        this.userData = UserData.createUserData();
        userRepository.setCurrentUser(UserData.createUserData());
        return "login";
    }

    @PostMapping("/register")
    public String register(UserData userData, Model model) {
        System.out.println(userData);
        UserRepository.formError formError = userRepository.addUser(userData);
        if (formError == formError.OK) {
            this.userData = userData;
            model.addAttribute("error", "Registration successful");
        } else {
            model.addAttribute("error", formError.text);
        }
        return "user-site";
    }

    @GetMapping("/register")
    public String registerGet(UserData userData, Model model) {
        return "user-site";
    }


}
