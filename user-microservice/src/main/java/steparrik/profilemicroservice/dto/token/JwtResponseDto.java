package steparrik.profilemicroservice.dto.token;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponseDto {
    private String token;
}
