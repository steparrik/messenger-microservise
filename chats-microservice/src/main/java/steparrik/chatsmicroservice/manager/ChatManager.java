package steparrik.chatsmicroservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.service.ChatService;
import steparrik.chatsmicroservice.service.UserService;
import steparrik.chatsmicroservice.utils.exceptions.ExceptionEntity;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatManager {
    private final UserService userService;
    private final ChatService chatService;

    public ResponseEntity<?> getChats(Principal principal){
        Optional<User> user = userService.findUserByUsername(principal.getName());
        return ResponseEntity.ok(chatService.chats(user.get()));
    }

}
