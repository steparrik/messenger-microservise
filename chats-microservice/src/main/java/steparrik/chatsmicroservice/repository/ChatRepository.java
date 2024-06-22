package steparrik.chatsmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findById(Long id);
    List<Chat> findAllByParticipants(User user);
}
