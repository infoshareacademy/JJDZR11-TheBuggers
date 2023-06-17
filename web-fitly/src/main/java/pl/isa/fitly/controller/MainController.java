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

    @GetMapping("/")
    public String mainSide() {
        return "main";
    }

    @GetMapping("/bmi")
    public String bmi(Model model) {
        model.addAttribute("userData", new UserData());
        return "bmi";
    }

    @PostMapping("/bmi")
    public String bmiSubmit(@Valid UserData userData, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("bmi", "Your BMI value: " + String.format("%.2f", userData.bmiValue()));
            model.addAttribute("bmiNS", userData.nutritionalStatus());
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

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }

}
