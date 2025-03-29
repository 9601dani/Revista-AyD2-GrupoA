package com.codenbugs.gateway.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.codenbugs.gateway.models.users.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.time}")
    private String jwtTime;

    @Value("${jwt.zone}")
    private String zone;

    private JWTVerifier verifier;

    @PostConstruct
    void init() {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        this.verifier = JWT.require(algorithm).build();
    }

    public String getToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.create()
                .withIssuer("codenbugs")
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withExpiresAt(getInstant(Integer.valueOf(jwtTime), zone))
                .sign(algorithm);
    }

    private Instant getInstant(Integer minutes, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return LocalDateTime.now().plusMinutes(minutes).toInstant(zonedDateTime.getOffset());
    }

    public DecodedJWT decodedJWT(String token) throws JWTVerificationException {
        return this.verifier.verify(token);
    }

    public String getClaim(String token, String claimName) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.decodedJWT(token);
        return decodedJWT.getClaim(claimName).asString();
    }

    private LocalDateTime getExpiredAtFromToken(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.decodedJWT(token);
        return decodedJWT.getExpiresAt().toInstant().atZone(ZoneId.of(zone)).toLocalDateTime();
    }

    public boolean isTokenExpired(String token) throws JWTVerificationException {
        LocalDateTime expiredAt = this.getExpiredAtFromToken(token);
        return LocalDateTime.now().isAfter(expiredAt);
    }
}
