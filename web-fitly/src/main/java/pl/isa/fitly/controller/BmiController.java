package pl.isa.fitly.controller;

import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;

@Controller
public class BmiController {
    UserData userData;
    UserRepository userRepository;

    public BmiController(UserData userData, UserRepository userRepository) {
        this.userData = userData;
        this.userRepository = userRepository;
    }

    @GetMapping("/bmi")
    public String bmi(Model model, Principal principal) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && principal != null) {
            userData = userRepository.getUserByEmail(principal.getName());
            model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
            model.addAttribute("bmiNS", userData.nutritionalStatus());
            model.addAttribute("userData", userData);
        } else {
            model.addAttribute("userData", UserData.createUserData());
        }

        return "bmi";
    }

    @PostMapping("/bmi")
    public String bmiSubmit(UserData userData, Model model) {
        model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
        model.addAttribute("bmiNS", userData.nutritionalStatus());
        return "bmi";
    }
}
