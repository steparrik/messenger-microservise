package steparrik.chatsmicroservice.utils.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}