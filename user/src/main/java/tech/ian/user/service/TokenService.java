package tech.ian.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.ian.user.entity.User;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var expiresIn = Instant.now().plus(Duration.ofMinutes(15));
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("BankXpert")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresIn)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Token não foi gerado", e);
        }
    }

    public String validadeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("BankXpert")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Usuário não verificado", e);
        }
    }

}
