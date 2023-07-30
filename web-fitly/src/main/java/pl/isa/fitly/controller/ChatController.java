package pl.isa.fitly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import pl.isa.fitly.chat.ChatMessage;
import pl.isa.fitly.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final Map<String, List<ChatMessage>> chatRoomsMessages = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat/{chatRoomId}")
    public void sendMessage(@DestinationVariable String chatRoomId, @Payload ChatMessage message) {
        // Ustaw nadawcę na aktualnie zalogowanego użytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        message.setSender(auth.getName());

        // Dodaj wiadomość do pokoju
        chatRoomsMessages.computeIfAbsent(chatRoomId, k -> new ArrayList<>()).add(message);

        // Wysyłamy wiadomość do konkretnego pokoju
        messagingTemplate.convertAndSend("/topic/messages/" + chatRoomId, message);
    }

    @SubscribeMapping("/topic/messages/{chatRoomId}")
    public List<ChatMessage> onSubscribe(@DestinationVariable String chatRoomId) {
        // Pobierz wiadomości z danego pokoju i zwróć je do nowego użytkownika do wyświetlenia
        return chatRoomsMessages.getOrDefault(chatRoomId, new ArrayList<>());
    }
}



