package com.codenbugs.ms_user.service;

import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1,"test","","test@gmail.com", "");
    }

    @Test
    void generateTokenSuccessfully()  {
        assertThrows(SettingNotFoundException.class, () -> tokenService.getToken(user));
    }
}
