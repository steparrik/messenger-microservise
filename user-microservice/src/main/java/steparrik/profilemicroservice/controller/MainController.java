package steparrik.profilemicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steparrik.profilemicroservice.dto.user.EditUserDto;
import steparrik.profilemicroservice.manager.AuthManager;
import steparrik.profilemicroservice.manager.EditManager;
import steparrik.profilemicroservice.manager.RegistrationManager;
import steparrik.profilemicroservice.manager.ProfileManager;
import steparrik.profilemicroservice.dto.user.AuthUserDto;
import steparrik.profilemicroservice.dto.user.RegistrationUserDto;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final AuthManager authManager;
    private final RegistrationManager registrationManager;
    private final ProfileManager profileManager;
    private final EditManager editManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody AuthUserDto authRequest){
        return authManager.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationUserDto registrationUserDto){
        return registrationManager.registration(registrationUserDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Principal principal) {
        return profileManager.myProfile(principal);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestBody EditUserDto editUserDto, Principal principal) {
        return editManager.editUser(editUserDto, principal);
    }


}
