package steparrik.profilemicroservice.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.service.RegistrationService;
import steparrik.profilemicroservice.service.UserService;
import steparrik.profilemicroservice.utils.exceptions.ExceptionEntity;
import steparrik.profilemicroservice.utils.mapper.user.RegistrationUserMapper;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationManager {
    private final RegistrationService registrationService;
    private final UserService userService;

    public ResponseEntity<?> registration(@RequestBody RegistrationUserDto registrationUserDto) {
        ExceptionEntity exceptionEntity = validateRegistrationDate(registrationUserDto);
        if(exceptionEntity!=null){
            return ResponseEntity.status(404).body(exceptionEntity);
        }else{
            return ResponseEntity.ok().body(registrationService.registration(registrationUserDto));
        }
    }

    public ExceptionEntity validateRegistrationDate(RegistrationUserDto registrationUserDto){
        if (registrationUserDto.getUsername() == null || registrationUserDto.getUsername().isEmpty()) {
            return new ExceptionEntity("Ник - обязательное поле", LocalDateTime.now());
        }
        if (registrationUserDto.getPassword() == null || registrationUserDto.getPassword().isEmpty()) {
            return new ExceptionEntity("Пароль - обязательное поле", LocalDateTime.now());
        }
        if (userService.findUserByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ExceptionEntity("Пользователь с таким ником уже существует", LocalDateTime.now());
        }
        if (userService.findUserByPhoneNumber(registrationUserDto.getPhoneNumber()).isPresent()) {
            return new ExceptionEntity("Данный номер телефона уже используется другим пользователем", LocalDateTime.now());
        }
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ExceptionEntity("Пароли не совпадают", LocalDateTime.now());
        }
        return null;
    }
}
