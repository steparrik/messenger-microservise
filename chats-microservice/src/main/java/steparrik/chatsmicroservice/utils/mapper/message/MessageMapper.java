package steparrik.chatsmicroservice.utils.mapper.message;

import org.mapstruct.Mapper;
import steparrik.chatsmicroservice.dto.message.MessageDTO;
import steparrik.chatsmicroservice.model.message.Message;
import steparrik.chatsmicroservice.utils.mapper.Mappable;

@Mapper(componentModel = "spring")
public interface MessageMapper extends Mappable<Message, MessageDTO> {
}
