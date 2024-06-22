package steparrik.chatsmicroservice.dto.dtoExceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExceptionDto {
    private String message;
    private LocalDateTime localDateTime;
}

