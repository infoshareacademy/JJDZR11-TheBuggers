package pl.isa.fitly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.security.Principal;
import java.util.UUID;

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

    @GetMapping("/profile/{email}/add-contact")
    public ResponseEntity<String> addContact(@PathVariable("email") String recipientEmail, Principal principal) {
        String senderEmail = principal.getName(); // Get the email of the currently logged-in user

        // Generate the chat room ID based on sender and recipient emails
        String chatRoomId = generateChatRoomId(senderEmail, recipientEmail);

        // Add the chat room ID to both the sender and recipient users
        userRepository.addChatRoom(senderEmail, chatRoomId);
        userRepository.addChatRoom(recipientEmail, chatRoomId);

        // Return a response indicating success
        return ResponseEntity.ok("Contact added successfully. You can now start a chat.");
    }

    private String generateChatRoomId(String senderEmail, String recipientEmail) {
        // Generowanie losowego identyfikatora UUID
        return UUID.randomUUID().toString();
    }
}
