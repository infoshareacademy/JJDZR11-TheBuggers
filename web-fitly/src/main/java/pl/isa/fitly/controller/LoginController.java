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

//    @GetMapping("/login1")
//    public String usersite(Model model) {
//        model.addAttribute("userData", userData);
//        userRepository.setUserLoggedIn(true);
//        return "user-site";
//    }
//    @PostMapping("/login1")
//    public String login(UserData userData, Model model) {
//        int error = userRepository.userLogin(userData.getEmail(), userData.getPassword()).ordinal();
//        switch (UserRepository.formError.values()[error]) {
//            case OK -> {
//                this.userData = userRepository.getUserByEmail(userData.getEmail());
//                model.addAttribute("info", "Logged in correctly");
//            }
//            case INCORRECT_PASSWORD -> model.addAttribute("info", UserRepository.formError.INCORRECT_PASSWORD.text);
//            case NOT_FOUND_USER -> model.addAttribute("info", UserRepository.formError.NOT_FOUND_USER.text);
//        }
//        return "user-site";
//    }

    @GetMapping("/login")
    public String userSite(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("userName", "Current user: " + principal.getName());
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout(UserData userData, Model model) {
        this.userData = new UserData();
        return "user-site";
    }

    @PostMapping("/register")
    public String register(UserData userData, Model model) {
        UserRepository.formError formError = userRepository.addUser(userData);
        if (formError == UserRepository.formError.OK) {
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
