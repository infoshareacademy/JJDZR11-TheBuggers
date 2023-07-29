package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile/{email}")
    public String showProfilePage(@PathVariable("email") String email, Model model) {
        UserData userData = userRepository.getUserByEmail(email);
        model.addAttribute("userData", userData);
        return "profile";
    }

    @GetMapping("/profile/{email}/chat")
    public String startChat(@PathVariable("email") String email) {
        // Your logic to start the chat with the user identified by the email
        return "redirect:/chat"; // Przekierowanie na stronę czatu po rozpoczęciu rozmowy
    }
}
