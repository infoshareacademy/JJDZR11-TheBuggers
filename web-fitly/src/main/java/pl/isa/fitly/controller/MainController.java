package pl.isa.fitly.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;

@Controller
public class MainController {

    @GetMapping("/bmi")
    public String bmi(Model model){
        model.addAttribute("userData", new UserData());
        model.addAttribute("bmi","");
        model.addAttribute("bmiNS","");
        return "bmi";
    }

    @PostMapping("/bmi")
    public String bmiSubmit(@ModelAttribute UserData userData, Model model) {
        model.addAttribute("userData", userData);
        model.addAttribute("bmi","Your BMI value: "+String.format("%.2f",userData.bmiValue()));
        model.addAttribute("bmiNS",userData.nutritionalStatus());
        return "bmi";
    }
}
