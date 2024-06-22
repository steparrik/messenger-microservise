package steparrik.frontendforrestmessanger.controller;


import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import steparrik.frontendforrestmessanger.dto.MessageDTO;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage/{chatId}")
    @SendTo("/topic/public/{chatId}")
    public MessageDTO sendMessage(@DestinationVariable String chatId, @Payload MessageDTO chatMessage) {
        return chatMessage;
    }



}
