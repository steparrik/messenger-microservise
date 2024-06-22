package steparrik.profilemicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steparrik.profilemicroservice.dto.exception.UserExceptionDto;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.repository.UserRepository;
import steparrik.profilemicroservice.dto.user.ProfileUserDto;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;

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
    private final ProfileUserMapper profileUserMapper;
    private final RegistrationUserMapper registrationUserMapper;



    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public ResponseEntity<?> userProfile(Principal principal){
        if(principal!=null) {
            User user = findUserByUsername(principal.getName()).orElse(null);
            ProfileUserDto profileUserDto = profileUserMapper.toDto(user);
            return ResponseEntity.ok(profileUserDto);
        }else{
            return new ResponseEntity(new UserExceptionDto("Вы не авторизированы", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> registration(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new UserExceptionDto("Пароли не совпадают", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        User user = new User(registrationUserDto.getPassword(), registrationUserDto.getUsername(), registrationUserDto.getPhoneNumber(), registrationUserDto.getFullName());

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return new ResponseEntity<>(new UserExceptionDto("Ник - обязательное поле", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ResponseEntity<>(new UserExceptionDto("Пароль - обязательное поле", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.searchByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(new UserExceptionDto("Пользователь с таким ником уже существует", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.searchByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return new ResponseEntity<>(new UserExceptionDto("Данный номер телефона уже используется другим пользователем", LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        save(user);
        log.info(String.format("user %s was be  registered", user.getUsername()));
        return ResponseEntity.ok(profileUserMapper.toDto(user));
    }


    public void save(User user) {
        userRepository.save(user);
    }






}
