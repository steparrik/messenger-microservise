package steparrik.chatsmicroservice.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.message.Message;


import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String password;

    @NotEmpty
    private String username;

    @NotEmpty
    private String phoneNumber;

    private String fullName;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    @ManyToMany(mappedBy = "participants")
    private List<Chat> chats;

    public User(String password, String username, String phoneNumber, String fullName) {
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }
}
