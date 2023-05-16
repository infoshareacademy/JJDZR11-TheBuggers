package pl.isa.fitly.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/main")
    public String mainPage(Model model) {
        // Add any necessary model attributes
        return "main"; // Returns the template name without the extension
    }

    @GetMapping("/")
    public String home() {
        return "main";
    }
}
