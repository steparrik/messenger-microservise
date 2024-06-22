package steparrik.profilemicroservice.dto.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserExceptionDto {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
}
