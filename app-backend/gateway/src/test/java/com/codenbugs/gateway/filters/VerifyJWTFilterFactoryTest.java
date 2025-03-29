package com.codenbugs.gateway.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.codenbugs.gateway.exceptions.user.NotAllowedException;
import com.codenbugs.gateway.models.users.User;
import com.codenbugs.gateway.repositories.user.UserRepository;
import com.codenbugs.gateway.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VerifyJWTFilterFactoryTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private GatewayFilterChain chain;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private HttpHeaders headers;

    @InjectMocks
    private VerifyJWTFilterFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(this.exchange.getRequest()).thenReturn(request);
        when(this.request.getHeaders()).thenReturn(headers);
    }

    @Test
    void shouldApplyWithValidToken() {
        String validToken = "Bearer validToken";
        String username = "testuser";
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setAuthToken(validToken);
        when(this.headers.getFirst("Authorization")).thenReturn(validToken);
        when(this.tokenService.isTokenExpired("validToken")).thenReturn(false);
        when(this.tokenService.getClaim("validToken", "username")).thenReturn(username);
        when(this.tokenService.getClaim("validToken", "email")).thenReturn(email);
        when(this.userRepository.existsByUsernameAndEmailAndAuthToken(username, email, "validToken")).thenReturn(true);

        when(chain.filter(exchange)).thenReturn(Mono.empty());

        this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);

        verify(this.tokenService).getClaim("validToken", "username");
        verify(this.userRepository).existsByUsernameAndEmailAndAuthToken(username, email, "validToken");

    }

    @Test
    void shouldNotFindAuthHeader() {
        when(this.headers.getFirst("Authorization")).thenReturn(null);

        Exception exception = assertThrows(NotAllowedException.class, () -> {
            this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);
        });

        String expectedMessage = "No se encontró el JWT token.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldNotFindBearer() {
        String notValidToken = "Not valid token";

        when(this.headers.getFirst("Authorization")).thenReturn(notValidToken);

        Exception exception = assertThrows(NotAllowedException.class, () -> {
            this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);
        });

        String expectedMessage = "No se encontró el JWT token.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldGetExpiredToken() {
        String validToken = "Bearer validToken";

        when(this.headers.getFirst("Authorization")).thenReturn(validToken);
        when(this.tokenService.isTokenExpired("validToken")).thenReturn(true);

        Exception exception = assertThrows(NotAllowedException.class, () -> {
            this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);
        });

        String expectedMessage = "La sesión ha expirado.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void shouldNotFindUser() {
        String validToken = "Bearer validToken";
        String username = "testuser";
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setAuthToken(validToken);
        when(this.headers.getFirst("Authorization")).thenReturn(validToken);
        when(this.tokenService.isTokenExpired("validToken")).thenReturn(false);
        when(this.tokenService.getClaim("validToken", "username")).thenReturn(username);
        when(this.tokenService.getClaim("validToken", "email")).thenReturn(email);
        when(this.userRepository.existsByUsernameAndEmailAndAuthToken(username, email, "validToken")).thenReturn(false);

        Exception exception = assertThrows(NotAllowedException.class, () -> {
            this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);
        });

        String expectedMessage = "No se encontró al usuario.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldThrowJWTVerificationException() {
        String validToken = "Bearer validToken";
        String username = "testuser";
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setAuthToken(validToken);
        when(this.headers.getFirst("Authorization")).thenReturn(validToken);
        when(this.tokenService.isTokenExpired("validToken")).thenReturn(false);
        when(this.tokenService.getClaim("validToken", "username")).thenThrow(JWTVerificationException.class);

        Exception exception = assertThrows(NotAllowedException.class, () -> {
            this.factory.apply(new VerifyJWTFilterFactory.Config()).filter(exchange, chain);
        });

        String expectedMessage = "La sesión ha expirado.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}