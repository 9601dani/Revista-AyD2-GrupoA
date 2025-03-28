package com.codenbugs.gateway.services;

import com.codenbugs.gateway.models.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenService, "jwtSecret", "mytestsecret");
        ReflectionTestUtils.setField(tokenService, "jwtTime", "60");
        ReflectionTestUtils.setField(tokenService, "zone", "America/Guatemala");

        tokenService.init();

        mockUser = new User();
        mockUser.setEmail("email@example.com");
        mockUser.setUsername("username");
        mockUser.setPassword("password");
    }

    @Test
    void testGetToken() {
        String token = tokenService.getToken(mockUser);
        assertNotNull(token);
    }

    @Test
    void shouldGetClaim() {
        String token = tokenService.getToken(mockUser);
        String expectedClaim = "username";
        String actualClaim = tokenService.getClaim(token, "username");
        assertEquals(expectedClaim, actualClaim);
    }

    @Test
    void shouldReturnExpiredFalse() {
        String token = tokenService.getToken(mockUser);
        boolean actualValue = tokenService.isTokenExpired(token);
        assertFalse(actualValue);
    }

}