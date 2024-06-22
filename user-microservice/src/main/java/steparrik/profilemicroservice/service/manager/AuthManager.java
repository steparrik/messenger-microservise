package steparrik.profilemicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.profilemicroservice.service.AuthService;
import steparrik.profilemicroservice.dto.user.AuthUserDto;

@Component
@RequiredArgsConstructor
public class AuthManager {
    private final AuthService authService;

    public ResponseEntity<?> createAuthToken(@RequestBody AuthUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }
}
