package steparrik.profilemicroservice.model.chat;

import jakarta.persistence.*;
import lombok.*;
import steparrik.profilemicroservice.model.message.Message;
import steparrik.profilemicroservice.model.user.User;

import java.util.List;

@Entity
@Table(name = "chats")
@ToString
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameGroup;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "chats"),
            inverseJoinColumns = @JoinColumn(name ="participants"))
    private List<User> participants;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @OneToMany(mappedBy = "chats")
    private List<Message> messages;

}


//Передавать Principal чтобы фронт понимал имя чата
