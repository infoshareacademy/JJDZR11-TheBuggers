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
    public String showContactsPage(Model model, Principal principal, HttpServletRequest request) {
        String userEmail = principal.getName();
        List<ContactInfo> contacts = userRepository.getContactsForUser(userEmail);

        // Check if the contacts contain the old format links
        for (ContactInfo contact : contacts) {
            if (contact.getRoomId().contains("@")) {
                // If yes, generate the new link and redirect the user
                String newRoomId = contact.getRoomId().replace("@", "_");
                String redirectUrl = request.getContextPath() + "/chat/" + newRoomId;
                return "redirect:" + redirectUrl;
            }
        }

        // If no old links are found, display the contacts page
        model.addAttribute("contacts", contacts);
        return "contacts";
    }
}