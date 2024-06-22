package steparrik.profilemicroservice.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.profilemicroservice.service.UserService;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class ProfileManager {
    private final UserService userService;

    public ResponseEntity<?> myProfile(Principal principal) {
        return userService.userProfile(principal);
    }
}
