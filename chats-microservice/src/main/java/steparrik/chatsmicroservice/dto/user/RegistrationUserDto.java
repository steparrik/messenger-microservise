package steparrik.chatsmicroservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
