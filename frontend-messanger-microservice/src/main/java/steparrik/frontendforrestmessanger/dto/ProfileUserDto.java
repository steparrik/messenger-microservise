package steparrik.frontendforrestmessanger.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileUserDto {
    private String username;

    private String phoneNumber;

    private String fullName;
}
