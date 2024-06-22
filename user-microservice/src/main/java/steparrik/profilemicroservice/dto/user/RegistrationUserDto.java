package steparrik.profilemicroservice.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import steparrik.profilemicroservice.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDto {
    private String password;
    private String confirmPassword;
    private String username;
    private String phoneNumber;
    private String fullName;
}
