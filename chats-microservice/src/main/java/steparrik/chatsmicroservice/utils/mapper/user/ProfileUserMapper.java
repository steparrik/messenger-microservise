package steparrik.chatsmicroservice.utils.mapper.user;

import org.mapstruct.Mapper;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.dto.user.ProfileUserDto;
import steparrik.chatsmicroservice.utils.mapper.Mappable;


@Mapper(componentModel = "spring")
public interface ProfileUserMapper extends Mappable<User, ProfileUserDto> {
}
