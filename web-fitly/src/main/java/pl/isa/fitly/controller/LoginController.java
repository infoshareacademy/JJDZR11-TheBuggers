package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;
import pl.isa.fitly.service.UserService;

import java.security.Principal;

@Controller
public class LoginController {
    UserData userData;
    UserRepository userRepository;
    UserService userService;

    public LoginController(UserData userData, UserRepository userRepository, UserService userService) {
        this.userData = userData;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String userSite(Model model) {
        if (userRepository.isCurrentUser()) {
            model.addAttribute("userName", "Current user: " + userRepository.getCurrentUser().getEmail());
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "login";

//        TODO - zrobić tak, żeby button LOGOUT wyświetlał się na layout.html a nie tylko na login.html jak to sugeruje return "login";

    }

//    @GetMapping("/logoutButton")
//    public String logoutButton(Model model) {
//        model.addAttribute("logoutButton", true);
//        return "layout";
//    }
//
//    @GetMapping("/logoutButton")
//    public String logoutButton(Model model) {
//        if (userRepository.getCurrentUser() != null && !userRepository.getCurrentUser().emptyUser()) {
//            model.addAttribute("logoutButton", true);
//        }
//        return "layout";
//    }

//    @PostMapping("/logout")
//    public String logout(UserData userData, Model model) {
//        this.userData = UserData.createUserData();
//        userRepository.setCurrentUser(UserData.createUserData());
//        return "login";
//    }

    @PostMapping("/register")
    public String register(UserData userData, Model model) {
        UserRepository.formError formError = userService.createUser(userData);
        if (formError == formError.OK) {
            this.userData = userData;
            return "login";
        } else {
            model.addAttribute("error", formError.text);
        }
        return "user-site";
    }

    @GetMapping("/register")
    public String registerGet(UserData userData, Model model) {
        return "user-site";
    }

    @GetMapping("/userUpdate")
    public String updateUserGet(Model model, Principal principal) {
        if (principal != null) {
            userData=userRepository.getUserFromPrincipal(principal);
            model.addAttribute("userData", userData);
        } else {
            model.addAttribute("userData", UserData.createUserData());
        }

        return "userUpdate";
    }

    @PostMapping("/userUpdate")
    public String updateUserPost(UserData userData, Model model, Principal principal) {
        String email = principal.getName();
        UserRepository.formError formError = userService.userUpdate(email,userData);
        if (formError == formError.OK) {
            this.userData = userData;
            return "main";
        } else {
            model.addAttribute("error", formError.text);
        }
        return "userUpdate";
    }


}
