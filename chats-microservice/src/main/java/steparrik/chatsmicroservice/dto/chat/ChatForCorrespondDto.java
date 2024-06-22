package steparrik.chatsmicroservice.dto.chat;

import lombok.*;
import steparrik.chatsmicroservice.dto.message.MessageDTO;
import steparrik.chatsmicroservice.dto.user.ProfileUserDto;
import steparrik.chatsmicroservice.model.chat.ChatType;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatForCorrespondDto extends ChatForMenuChatsDto {
    private Long id;

    private String name;

    private List<ProfileUserDto> participants;

    private ChatType chatType;

    private List<MessageDTO> messages;
}
