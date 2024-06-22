package steparrik.chatsmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import steparrik.chatsmicroservice.dto.dtoExceptions.ExceptionDto;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.repository.UserRepository;
import steparrik.chatsmicroservice.dto.user.RegistrationUserDto;
import steparrik.chatsmicroservice.utils.mapper.user.ProfileUserMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ProfileUserMapper profileUserMapper;


    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public ResponseEntity<?> registration(RegistrationUserDto registrationUserDto) {
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            return new ResponseEntity(new ExceptionDto("Пароли не совпадают", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        User user = new User(registrationUserDto.getPassword(), registrationUserDto.getUsername(), registrationUserDto.getPhoneNumber(),registrationUserDto.getFullName());

        if(user.getUsername() == null || user.getUsername().isEmpty()){
            return new ResponseEntity(new ExceptionDto("Ник - обязательное поле", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            return new ResponseEntity(new ExceptionDto("Пароль - обязательное поле", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.searchByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity(new ExceptionDto("Пользователь с таким ником уже существует", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.searchByPhoneNumber(user.getPhoneNumber()).isPresent()){
            return new ResponseEntity(new ExceptionDto("Данный номер телефона уже используется другим пользователем", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info(String.format("user %s was be  registered", user.getUsername()));
        return ResponseEntity.ok(null);
    }


    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> searchByUsernameOrPhoneNumber(String username, String phoneNumber){
        Optional<User> user = userRepository.searchByUsernameOrPhoneNumber(username, phoneNumber);
        return user;
    }



}
