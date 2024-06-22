package steparrik.profilemicroservice.utils.mapper.user;

import org.mapstruct.Mapper;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.dto.user.ProfileUserDto;
import steparrik.profilemicroservice.utils.mapper.Mappable;

@Mapper(componentModel = "spring")
public interface ProfileUserMapper extends Mappable<User, ProfileUserDto> {
}
