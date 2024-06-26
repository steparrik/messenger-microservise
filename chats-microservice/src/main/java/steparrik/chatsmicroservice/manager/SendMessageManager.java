package steparrik.chatsmicroservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.chatsmicroservice.dto.message.MessageDTO;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.message.Message;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.service.ChatService;
import steparrik.chatsmicroservice.service.MessageService;
import steparrik.chatsmicroservice.service.UserService;
import steparrik.chatsmicroservice.utils.mapper.message.MessageMapper;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SendMessageManager {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ChatService chatService;
    private final UserService userService;
    private final DefiniteChatManager definiteChatManager;

    public ResponseEntity<?> sendMessage(long id, MessageDTO messageDTO, Principal principal) {
        Message message = messageMapper.toEntity(messageDTO);
        User sender = userService.findUserByUsername(principal.getName()).get();
        Optional<Chat> chat = chatService.findChatById(id);
        if(chat.isPresent()){
            message.setSender(sender);
            message.setChats(chat.get());
            message.setTimestamp(new Date());
            messageService.save(message);
            return ResponseEntity.ok(messageMapper.toDto(message));
        }else{
            return ResponseEntity.status(404).body("Нет чата с данным ID");
        }
    }
}
