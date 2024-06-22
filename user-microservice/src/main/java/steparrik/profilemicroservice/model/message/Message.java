package steparrik.profilemicroservice.model.message;

import jakarta.persistence.*;
import lombok.*;
import steparrik.profilemicroservice.model.chat.Chat;
import steparrik.profilemicroservice.model.user.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "messages")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageText;

    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chats;
}
