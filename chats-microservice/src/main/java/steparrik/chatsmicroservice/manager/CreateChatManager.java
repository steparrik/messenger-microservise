package steparrik.chatsmicroservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.chatsmicroservice.dto.chat.ChatForMenuChatsDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.utils.exceptions.ExceptionEntity;
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
        if(chatForMenuChatsDto == null &&
                (username == null || username.isEmpty())&&
                (phoneNumber == null || phoneNumber.isEmpty())) {
            return new ResponseEntity<>(new ExceptionEntity("Недостаточно данных", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if(chatForMenuChatsDto == null){
            chatForMenuChatsDto = new ChatForMenuChatsDto();
        }

        Optional<User> owner = userService.findUserByUsername(principal.getName());
        Optional<User> userForAdd = userService.searchByUsernameOrPhoneNumber(username, phoneNumber);
        List<User> participants = new ArrayList<>();

        if(userForAdd.isPresent()){
            if(userForAdd.get().getUsername().equals(owner.get().getUsername())){
                return new ResponseEntity<>(new ExceptionEntity("Вы не можете создать чат с собой", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
            }
            if(chatService.findYetAddedDialogs(owner.get(), userForAdd.get())){
                return new ResponseEntity<>(new ExceptionEntity("Чат с этим пользователем уже существует", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
            }
            chatForMenuChatsDto.setChatType(ChatType.DIALOG);
            participants.add(userForAdd.get());
            participants.add(owner.get());
            Chat chat = chatService.createChat(chatForMenuChatsDto, participants);
            return ResponseEntity.ok(chatService.chooseDialogName(owner.get(), chatForMenuChatsMapper.toDto(chat), chat));
        }else {
            if(username!=null || phoneNumber!=null){
                return new ResponseEntity<>(new ExceptionEntity("Пользователь не найден", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
            }
            chatForMenuChatsDto.setChatType(ChatType.GROUP);
            participants.add(owner.get());
            Chat chat = chatService.createChat(chatForMenuChatsDto, participants);
            return ResponseEntity.ok(chatService.chooseDialogName(owner.get(), chatForMenuChatsMapper.toDto(chat), chat));
        }
    }

}
