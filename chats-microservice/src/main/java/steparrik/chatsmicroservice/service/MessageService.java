package steparrik.chatsmicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steparrik.chatsmicroservice.model.message.Message;
import steparrik.chatsmicroservice.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void save(Message message){
        messageRepository.save(message);
    }
}
