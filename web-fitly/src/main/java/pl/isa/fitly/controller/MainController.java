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
    UserData userData;
    UserController userController;
    boolean userLoggedIn;

    public MainController(UserData userData, UserController userController) {
        this.userData = userData;
        this.userController = userController;
    }

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
    public String bmiSubmit(UserData userData, Model model) {
        model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
        model.addAttribute("bmiNS", userData.nutritionalStatus());
        return "bmi";
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

    @PostMapping("/register")
    public String register(UserData userData, Model model) {
        this.userData = userData;
        model.addAttribute("Error", userController.addUser(userData).text);
        return "usersite";
    }

    @PostMapping("/logout")
    public String logout(UserData userData, Model model) {
        this.userData = new UserData();
        return "usersite";
    }

    @PostMapping("/login")
    public String login(UserData userData, Model model) {
        int error = userController.userLogin(userData.getEmail(), userData.getPassword()).ordinal();
        switch (UserController.formError.values()[error]) {
            case OK -> {
                this.userData = userController.getUserByEmail(userData.getEmail());
                model.addAttribute("Info", "Logged in correctly");
            }
            case INCORRECT_PASSWORD -> model.addAttribute("Info", UserController.formError.INCORRECT_PASSWORD.text);
            case NOT_FOUND_USER -> model.addAttribute("Info", UserController.formError.NOT_FOUND_USER.text);
        }
        return "usersite";
    }

}
