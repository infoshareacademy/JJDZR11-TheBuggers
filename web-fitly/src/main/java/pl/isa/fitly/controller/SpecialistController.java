package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.util.List;

@Controller
public class SpecialistController {

    private final UserRepository userRepository;

    public SpecialistController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/specialist")
    public String showSpecialistPage(Model model) {
        List<UserData> specialists = userRepository.getSpecialists();
        model.addAttribute("specialists", specialists);
        return "specialist";
    }

}

