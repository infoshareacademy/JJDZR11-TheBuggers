package pl.isa.fitly.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;


@Controller
public class MainController {
    UserData userData;
    UserRepository userRepository;


    public MainController(UserData userData, UserRepository userRepository) {
        this.userData = userData;
        this.userRepository = userRepository;
    }

    @GetMapping("/m")
    public String mainSide() {
        return "main";
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



}
