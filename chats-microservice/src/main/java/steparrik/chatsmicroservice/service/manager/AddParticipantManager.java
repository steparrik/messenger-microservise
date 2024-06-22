package steparrik.chatsmicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.chat.ChatType;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.service.ChatService;
import steparrik.chatsmicroservice.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddParticipantManager {
    private final UserService userService;
    private final ChatService chatService;


    public ResponseEntity<?> addParticipant(long id, String username, String phoneNumber, Principal principal) {
        if((username==null && phoneNumber==null) || (username.isEmpty() && phoneNumber.isEmpty())){
            return ResponseEntity.status(404).body("Недостаточно данных");
        }
        Optional<Chat> chat = chatService.findChatById(id);
        User principalUser = userService.findUserByUsername(principal.getName()).get();
        if(chat.isPresent()){
            if(chat.get().getChatType().equals(ChatType.DIALOG)){
                return ResponseEntity.status(404).body("Добавлять людей в DIALOG нельзя");
            }
            if(!chat.get().getParticipants().contains(principalUser)){
                return ResponseEntity.status(404).body("Чат с данным id не найден в списке ваших чатов");
            }
            Optional<User> userForAdd = userService.searchByUsernameOrPhoneNumber(username, phoneNumber);
            if(userForAdd.isPresent()){
                if(chat.get().getParticipants().contains(userForAdd.get())){
                    return ResponseEntity.status(404).body("Пользователь " + userForAdd.get().getUsername()+ " уже добавлен");
                }
                chat.get().getParticipants().add(userForAdd.get());
                chatService.save(chat.get());
                return ResponseEntity.ok().body(null);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с таким ником или номером телефона не найден");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет чата с таким ID");
        }
    }
}
