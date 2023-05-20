package pl.isa.fitly.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;


@Controller
public class MainController {
    UserData userData = new UserData();
    UserController userController = new UserController();

    @GetMapping("/")
    public String mainSide() {
        return "main";
    }

    @GetMapping("/bmi")
    public String bmi(Model model) {
        model.addAttribute("userData", userData);
        return "bmi";
    }

    @PostMapping("/bmi")
    public String bmiSubmit(@Valid UserData userData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (!errorInputBmi(bindingResult)) {
                model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
                model.addAttribute("bmiNS", userData.nutritionalStatus());
            } else {
                model.addAttribute("bmi", "Error  " + bindingResult.getAllErrors().get(0));
            }
        }
        return "bmi";
    }

    static boolean errorInputBmi(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (fieldError.getField().equals("height") || fieldError.getField().equals("weight")) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/calories")
    public String calories() {
        return "calories";
    }

    @GetMapping("/diets")
    public String diets() {
        return "diets";
    }

    @GetMapping("/trainings")
    public String trainings() {
        return "trainings";
    }

    @GetMapping("/login")
    public String usersite(Model model) {
        model.addAttribute("userData", userData);
        return "usersite";
    }

    @PostMapping("/login")
    public String login(@Valid UserData userData, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            this.userData = userData;
            model.addAttribute("Error", userController.addUser(userData).text);
            System.out.println(userData.toString());
        }
        return "usersite";
    }
}
