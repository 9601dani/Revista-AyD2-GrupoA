package com.codenbugs.ms_user.services.token;

import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwt_secret;

    @Value("${jwt.time}")
    private String jwt_time;

    @Value("${jwt.zone}")
    private String zone;


    public String getToken(User user) {
        

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
