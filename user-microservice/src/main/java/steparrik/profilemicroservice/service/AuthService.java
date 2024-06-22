package steparrik.profilemicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.profilemicroservice.dto.exception.UserExceptionDto;
import steparrik.profilemicroservice.dto.user.AuthUserDto;
import steparrik.profilemicroservice.dto.token.JwtResponseDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailService userDetailService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> createAuthToken(@RequestBody AuthUserDto authRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()));
        } catch (
                BadCredentialsException e) {
            return new ResponseEntity<>(new UserExceptionDto("Неправильный логин или пароль", LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }


        UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
