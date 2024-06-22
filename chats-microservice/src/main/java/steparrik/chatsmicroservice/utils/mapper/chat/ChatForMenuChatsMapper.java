package steparrik.chatsmicroservice.utils.mapper.chat;

import org.mapstruct.Mapper;
import steparrik.chatsmicroservice.dto.chat.ChatForMenuChatsDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.utils.mapper.Mappable;


@Mapper(componentModel = "spring")
public interface ChatForMenuChatsMapper extends Mappable<Chat, ChatForMenuChatsDto> {
}
