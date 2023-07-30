package pl.isa.fitly.chat;

import lombok.*;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

  private MessageType type;
  private String content;
  private String sender;

}
