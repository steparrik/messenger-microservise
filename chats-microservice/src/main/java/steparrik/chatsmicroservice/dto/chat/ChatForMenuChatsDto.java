package steparrik.chatsmicroservice.dto.chat;

import lombok.*;
import steparrik.chatsmicroservice.model.chat.ChatType;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class ChatForMenuChatsDto {
    private Long id;

    private String name;

    private ChatType chatType;
}
