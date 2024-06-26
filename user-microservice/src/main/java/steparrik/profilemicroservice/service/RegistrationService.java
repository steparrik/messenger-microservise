package steparrik.profilemicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steparrik.profilemicroservice.dto.user.ProfileUserDto;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;
import steparrik.profilemicroservice.manager.RegistrationManager;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.repository.UserRepository;
import steparrik.profilemicroservice.utils.exceptions.ExceptionEntity;
import steparrik.profilemicroservice.utils.mapper.user.ProfileUserMapper;
import steparrik.profilemicroservice.utils.mapper.user.RegistrationUserMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserService userService;
    private final ProfileUserMapper profileUserMapper;
    private final  RegistrationUserMapper registrationUserMapper;

    public ProfileUserDto registration(RegistrationUserDto registrationUserDto) {
        User user = registrationUserMapper.toEntity(registrationUserDto);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        log.info(String.format("user %s was be  registered", user.getUsername()));
        return profileUserMapper.toDto(user);
    }

}
