package steparrik.chatsmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steparrik.chatsmicroservice.dto.chat.ChatForMenuChatsDto;
import steparrik.chatsmicroservice.dto.message.MessageDTO;
import steparrik.chatsmicroservice.manager.*;
import steparrik.chatsmicroservice.manager.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class MainController {
    private final CreateChatManager createChatManager;
    private final ChatManager chatManager;
    private final DefiniteChatManager definiteChatManager;
    private final SendMessageManager sendMessageManager;
    private final AddParticipantManager addParticipantManager;

    @GetMapping
    public ResponseEntity<?> chats(Principal principal) {
        return chatManager.getChats(principal);
    }

    @PostMapping
    public ResponseEntity<?> createChat(Principal principal,
                                        @RequestParam(required = false) String username, @RequestParam(required = false) String phoneNumber,
                                        @RequestBody(required = false) ChatForMenuChatsDto chatForMenuChatsDto){
        return createChatManager.createChat(principal, chatForMenuChatsDto, username, phoneNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> definiteChat(@PathVariable long id, Principal principal){
        return definiteChatManager.getDefiniteChat(id, principal);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> sendMessage(@PathVariable long id, @RequestBody MessageDTO messageDTO, Principal principal){
        return sendMessageManager.sendMessage(id, messageDTO, principal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> addNewParticipant(@PathVariable long id, @RequestParam(required = false) String username,
                                               @RequestParam(required = false) String phoneNumber, Principal principal){
        return  addParticipantManager.addParticipant(id, username, phoneNumber, principal);
    }

}
