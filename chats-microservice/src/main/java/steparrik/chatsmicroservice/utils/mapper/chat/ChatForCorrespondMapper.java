package steparrik.chatsmicroservice.utils.mapper.chat;

import org.mapstruct.Mapper;
import steparrik.chatsmicroservice.dto.chat.ChatForCorrespondDto;
import steparrik.chatsmicroservice.model.chat.Chat;
import steparrik.chatsmicroservice.utils.mapper.Mappable;

@Mapper(componentModel = "spring")
public interface ChatForCorrespondMapper extends Mappable<Chat, ChatForCorrespondDto> {
}
