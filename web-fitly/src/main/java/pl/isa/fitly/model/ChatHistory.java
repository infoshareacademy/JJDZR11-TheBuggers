package pl.isa.fitly.model;

import pl.isa.fitly.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatHistory {
    private List<ChatMessage> chatMessages;

    public ChatHistory() {
        chatMessages = new ArrayList<>();
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }
}