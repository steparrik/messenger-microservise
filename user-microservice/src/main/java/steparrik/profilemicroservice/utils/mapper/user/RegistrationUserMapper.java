package steparrik.profilemicroservice.utils.mapper.user;

import org.mapstruct.Mapper;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.utils.mapper.Mappable;

@Mapper(componentModel = "spring")
public interface RegistrationUserMapper extends Mappable<User, RegistrationUserDto> {
}
