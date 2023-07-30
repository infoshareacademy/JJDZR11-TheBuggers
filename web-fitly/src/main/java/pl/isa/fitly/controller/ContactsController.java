package pl.isa.fitly.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.isa.fitly.model.ContactInfo;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactsController {

    private final UserRepository userRepository;

    public ContactsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/contacts")
    public String showContactsPage(Model model, Principal principal) {
        String userEmail = principal.getName();
        List<ContactInfo> contacts = userRepository.getContactsForUser(userEmail);


        model.addAttribute("contacts", contacts);
        return "contacts";
    }
}