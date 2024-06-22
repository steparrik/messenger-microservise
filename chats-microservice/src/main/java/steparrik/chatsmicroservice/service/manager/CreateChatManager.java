package steparrik.chatsmicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.chatsmicroservice.dto.chat.ChatForMenuChatsDto;
import steparrik.chatsmicroservice.dto.dtoExceptions.ExceptionDto;
import steparrik.chatsmicroservice.model.chat.ChatType;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.service.ChatService;
import steparrik.chatsmicroservice.service.UserService;
import steparrik.chatsmicroservice.utils.mapper.chat.ChatForMenuChatsMapper;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateChatManager {
    private final ChatService chatService;
    private final UserService userService;
    private final ChatForMenuChatsMapper chatForMenuChatsMapper;

    public ResponseEntity<?> createChat(Principal principal, @RequestBody(required = false) ChatForMenuChatsDto chatForMenuChatsDto,
                                        String username, String phoneNumber){
        if(chatForMenuChatsDto == null && username == null && phoneNumber == null) {
            return new ResponseEntity<>(new ExceptionDto("Недостаточно данных", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if(chatForMenuChatsDto == null){
            chatForMenuChatsDto = new ChatForMenuChatsDto();
        }
        Optional<User> owner = userService.findUserByUsername(principal.getName());
        Optional<User> participant = userService.searchByUsernameOrPhoneNumber(username, phoneNumber);
        if(owner.isPresent()) {
            if(participant.isPresent()){
                if(chatService.findYetAddedDialogs(owner.get(), participant.get())){
                    return new ResponseEntity<>(new ExceptionDto("Чат с этим пользователем уже существует", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
                }
                chatForMenuChatsDto.setChatType(ChatType.DIALOG);
                chatForMenuChatsDto.setName(null);
                List<User> participants = new ArrayList<>();
                participants.add(participant.get());
                return chatService.createChat(owner.get(), chatForMenuChatsDto, participants);
            }else {
                if(username!=null || phoneNumber!=null){
                    return new ResponseEntity<>(new ExceptionDto("Пользователь не найден", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
                }
                chatForMenuChatsDto.setChatType(ChatType.GROUP);
                return chatService.createChat(owner.get(), chatForMenuChatsDto, new ArrayList<>());
            }
        }else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
