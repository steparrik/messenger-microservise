package steparrik.profilemicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import steparrik.profilemicroservice.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> searchByUsername(String username);
    Optional<User> searchByPhoneNumber(String phoneNumber);
}
