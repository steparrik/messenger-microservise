package steparrik.chatsmicroservice.utils.exceptions;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String message) {
        super(message);
    }
}

