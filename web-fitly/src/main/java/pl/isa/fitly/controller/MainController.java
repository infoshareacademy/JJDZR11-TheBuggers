package pl.isa.fitly.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;


@Controller
public class MainController {
    UserData userData = new UserData();
    @GetMapping("/")
    public String mainSide() {
        return "main";
    }

    @GetMapping("/bmi")
    public String bmi(Model model) {
        model.addAttribute("userData",  userData);
        return "bmi";
    }

    @PostMapping("/bmi")
    public String bmiSubmit(@Valid UserData userData, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
            model.addAttribute("bmiNS", userData.nutritionalStatus());
        }else {
            model.addAttribute("bmi", "Error"+ bindingResult.toString());
        }
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
        model.addAttribute("userData",  userData);
        return "usersite";
    }

    @PostMapping("/login")
    public String login(@Valid UserData userData , BindingResult bindingResult, Model model) {
       // model.addAttribute("userData", new UserData());
        if (!bindingResult.hasErrors()) {
            //model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
            //model.addAttribute("bmiNS", userData.nutritionalStatus());
            this.userData=userData;
        }
        return "usersite";
    }
}
