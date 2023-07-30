package pl.isa.fitly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.fitly.chat.ChatMessage;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    private final UserRepository userRepository;

    public ChatController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/chat/room")
    public String chatRoom() {
        // Pobierz informacje o aktualnie zalogowanym użytkowniku z SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Pobierz nazwę użytkownika (email) aktualnie zalogowanego użytkownika
        String name = authentication.getName();

        UserData userData = userRepository.getUserByEmail(name);
        if (userData == null) {
            // Jeśli użytkownik o podanej nazwie nie istnieje, możesz podjąć odpowiednią akcję, np. przekierować na stronę błędu lub zarejestrować nowego użytkownika.
            // W tym przykładzie zakładam, że jeśli użytkownik nie istnieje, dodamy go automatycznie do repozytorium z pustymi danymi.
            userData = new UserData();
            userData.setEmail(name);
            userRepository.addUser(userData);
        }

        // Kontynuuj pozostałą część kodu, pobierając nazwę użytkownika z obiektu "userData" i łącząc się z WebSocket
        String username = userData.getName().trim();
        return "chat"; // Zwracamy "chat" jako nazwę widoku, aby użytkownik został przekierowany do chatu
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Dodaj nazwę użytkownika do sesji WebSocket
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}

