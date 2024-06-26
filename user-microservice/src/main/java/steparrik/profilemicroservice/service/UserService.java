package steparrik.profilemicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.repository.UserRepository;
import steparrik.profilemicroservice.dto.user.ProfileUserDto;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;

import steparrik.profilemicroservice.utils.exceptions.ExceptionEntity;
import steparrik.profilemicroservice.utils.mapper.user.ProfileUserMapper;
import steparrik.profilemicroservice.utils.mapper.user.RegistrationUserMapper;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void save(User user){
        userRepository.save(user);
    }


}
