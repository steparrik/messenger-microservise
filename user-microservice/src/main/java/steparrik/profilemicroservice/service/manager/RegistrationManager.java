package steparrik.profilemicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;
import steparrik.profilemicroservice.service.UserService;

@Component
@RequiredArgsConstructor
public class RegistrationManager {
    private final UserService userService;

    public ResponseEntity<?> registration(@RequestBody RegistrationUserDto registrationUserDto) {
        return userService.registration(registrationUserDto);
    }
}
