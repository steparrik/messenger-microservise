package steparrik.profilemicroservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import steparrik.profilemicroservice.service.UserService;
import steparrik.profilemicroservice.utils.mapper.user.ProfileUserMapper;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class ProfileManager {
    private final UserService userService;
    private final ProfileUserMapper profileUserMapper;

    public ResponseEntity<?> myProfile(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok().body(profileUserMapper.toDto(userService.findUserByUsername(username).get()));
    }
}
