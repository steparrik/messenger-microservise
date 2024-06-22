package steparrik.chatsmicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.chatsmicroservice.dto.chat.ChatForCorrespondDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.chat.ChatType;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.service.ChatService;
import steparrik.chatsmicroservice.service.UserService;
import steparrik.chatsmicroservice.utils.mapper.chat.ChatForCorrespondMapper;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefiniteChatManager {
    private final UserService userService;
    private final ChatService chatService;
    private final ChatForCorrespondMapper chatForCorrespondMapper;

    public ResponseEntity<?> getDefiniteChat(long id, Principal principal){
        Optional<Chat> chat = chatService.findChatById(id);
        User user = userService.findUserByUsername(principal.getName()).get();
        if(chat.isPresent()) {
            ChatForCorrespondDto chatForCorrespondDto = chatForCorrespondMapper.toDto(chat.get());
            chatService.chooseDialogName(user, chatForCorrespondDto, chat.get());
            return ResponseEntity.ok(chatForCorrespondDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет чата с данным ID");
        }
    }
}
