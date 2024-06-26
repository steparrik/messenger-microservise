package steparrik.profilemicroservice.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import steparrik.profilemicroservice.model.chat.Chat;
import steparrik.profilemicroservice.model.message.Message;

import java.lang.reflect.Member;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty
    private String phoneNumber;

    private String FullName;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    @ManyToMany(mappedBy = "participants")
    private List<Chat> chats;


    public User(String password, String username, String phoneNumber, String fullName) {
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        FullName = fullName;
    }
}
