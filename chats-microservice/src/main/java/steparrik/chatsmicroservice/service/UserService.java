package steparrik.chatsmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import steparrik.chatsmicroservice.model.user.User;
import steparrik.chatsmicroservice.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> searchByUsernameOrPhoneNumber(String username, String phoneNumber){
        Optional<User> user = userRepository.searchByUsernameOrPhoneNumber(username, phoneNumber);
        return user;
    }



}
