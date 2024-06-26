package steparrik.profilemicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import steparrik.profilemicroservice.model.user.User;
import steparrik.profilemicroservice.model.user.UserWithDetails;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользовавтель %s не найден", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), List.of()
        );
    }

}
