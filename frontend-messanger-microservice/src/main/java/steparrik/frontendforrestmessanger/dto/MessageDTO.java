package steparrik.frontendforrestmessanger.dto;


import lombok.*;


import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;

    private String messageText;

    private Date timestamp;

    private ProfileUserDto sender;
}
