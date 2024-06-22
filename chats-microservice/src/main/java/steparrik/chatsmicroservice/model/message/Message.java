package steparrik.chatsmicroservice.model.message;

import jakarta.persistence.*;
import lombok.*;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.user.User;


import java.util.Date;

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
