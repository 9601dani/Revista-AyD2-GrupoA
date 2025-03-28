package com.codenbugs.gateway.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.codenbugs.gateway.exceptions.user.NotAllowedException;
import com.codenbugs.gateway.repositories.user.UserRepository;
import com.codenbugs.gateway.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class VerifyJWTFilterFactory extends AbstractGatewayFilterFactory<VerifyJWTFilterFactory.Config> {

    public final TokenService tokenService;
    public final UserRepository userRepository;

    public VerifyJWTFilterFactory(
            UserRepository userRepository,
            TokenService tokenService
    ) {
        super(Config.class);
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                ServerHttpRequest request = exchange.getRequest();
                String authHeader = request.getHeaders().getFirst("Authorization");

                if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new NotAllowedException("No se encontró el JWT token.");
                }

                String token = authHeader.substring(7);
                if(this.tokenService.isTokenExpired(token)) {
                    throw new NotAllowedException("La sesión ha expirado.");
                }

                String username = this.tokenService.getClaim(token, "username");
                String email = this.tokenService.getClaim(token, "email");

                boolean exists = this.userRepository.existsByUsernameAndEmailAndAuthToken(username, email, token);

                if(!exists) {
                    throw new NotAllowedException("No se encontró al usuario.");
                }

                return chain.filter(exchange);
            } catch(JWTVerificationException exception) {
                throw new NotAllowedException("La sesión ha expirado.");
            }
        };
    }

    public static class Config {}
}
