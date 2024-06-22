package steparrik.chatsmicroservice.dto.message;

import jakarta.persistence.*;
import lombok.*;
import steparrik.chatsmicroservice.dto.user.ProfileUserDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.model.user.User;

import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;

    private String messageText;

    private Date timestamp;

    private ProfileUserDto sender;
}
