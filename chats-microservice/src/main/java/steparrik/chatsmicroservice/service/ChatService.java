package steparrik.chatsmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import steparrik.chatsmicroservice.dto.chat.ChatForCorrespondDto;
import steparrik.chatsmicroservice.dto.chat.ChatForMenuChatsDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.chat.ChatType;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.repository.ChatRepository;
import steparrik.chatsmicroservice.utils.mapper.chat.ChatForMenuChatsMapper;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatForMenuChatsMapper chatForMenuChatsMapper;

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public Optional<Chat> findChatById(Long id) {
        return chatRepository.findById(id);
    }

    public List<ChatForMenuChatsDto> chats(User user) {
        return chatRepository.findAllByParticipants(user).stream().map(chat -> {
            ChatForMenuChatsDto chatForMenuChatsDto = chatForMenuChatsMapper.toDto(chat);
            chooseDialogName(user, chatForMenuChatsDto, chat);
            return chatForMenuChatsDto;
        }).collect(Collectors.toList());
    }

    public Chat createChat(ChatForMenuChatsDto chatForMenuChatsDto, List<User> participants) {
        Chat chat = chatForMenuChatsMapper.toEntity(chatForMenuChatsDto);
        chat.setParticipants(participants);
        chatRepository.save(chat);
        return chat;
    }

    public ChatForMenuChatsDto chooseDialogName(User user, ChatForMenuChatsDto chatForMenuChatsDto, Chat chat) {
        if (chatForMenuChatsDto.getChatType().equals(ChatType.DIALOG)) {
            if (chat.getParticipants().get(0).getUsername().equals(user.getUsername())) {
                chatForMenuChatsDto.setName(chat.getParticipants().get(1).getFullName());
            } else {
                chatForMenuChatsDto.setName(chat.getParticipants().get(0).getFullName());
            }
        }
        return chatForMenuChatsDto;
    }

    public boolean findYetAddedDialogs(User owner, User participant) {
        for(Chat chat : owner.getChats()){
            if(chat.getParticipants().contains(participant) && chat.getChatType().equals(ChatType.DIALOG)){
                return  true;
            }
        }
        return false;
    }


}
