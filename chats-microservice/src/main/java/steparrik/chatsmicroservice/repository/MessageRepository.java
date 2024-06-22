package steparrik.chatsmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import steparrik.chatsmicroservice.dto.message.MessageDTO;
import steparrik.chatsmicroservice.model.message.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
