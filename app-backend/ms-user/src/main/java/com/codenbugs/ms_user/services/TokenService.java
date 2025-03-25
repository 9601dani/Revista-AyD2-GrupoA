package com.codenbugs.ms_user.services;

import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.models.User;
import lombok.AllArgsConstructor;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class TokenService {

    public String getToken(User user) throws SettingNotFoundException {

        String jwt_secret = System.getenv("JWT_SECRET");
        String jwt_time = System.getenv("JWT_TIME");
        String zone = System.getenv("ZONE");

        if (jwt_secret == null || jwt_time == null || zone == null) {
            throw new SettingNotFoundException("No se encontro configuración");
        }

        Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

        return JWT.create()
                .withIssuer("codenbugs")
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withExpiresAt(getInstant(Integer.parseInt(jwt_time), zone))
                .sign(algorithm);
    }

    private Instant getInstant(Integer minutes, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return LocalDateTime.now().plusMinutes(minutes).toInstant(zonedDateTime.getOffset());
    }
}
