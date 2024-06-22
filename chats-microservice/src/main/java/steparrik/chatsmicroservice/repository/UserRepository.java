package steparrik.chatsmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import steparrik.chatsmicroservice.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> searchByUsername(String username);
    Optional<User> searchByPhoneNumber(String phoneNumber);
    Optional<User> searchByUsernameOrPhoneNumber(String username, String phoneNumber);
}
