package ru.sber.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.sber.security.services.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.app.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Недопустимый JWT-токен: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Срок действия JWT-токена истек: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT-токен не поддерживается: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Пустая строка у JWT-токена: {}", e.getMessage());
        }

        return false;
    }
}
