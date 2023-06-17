package pl.isa.fitly.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;


@Controller
public class MainController {
    UserData userData;
    UserRepository userRepository;


    public MainController(UserData userData, UserRepository userRepository) {
        this.userData = userData;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainSide(Principal principal) {
        if (principal != null) {
            userRepository.setCurrentUser(userRepository.getUserByEmail(principal.getName()));
        } else {
            userRepository.setCurrentUser(UserData.createUserData());
        }
        return "main";
    }

    @GetMapping("/calories")
    public String calories() {
        return "calories";
    }

    /*
    @GetMapping("/diets")
    public String diets() {
        return "diets";
    }

     */

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
