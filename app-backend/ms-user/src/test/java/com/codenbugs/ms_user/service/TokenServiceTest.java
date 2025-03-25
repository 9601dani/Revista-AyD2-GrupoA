package com.codenbugs.ms_user.service;

import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1,"test","","test@gmail.com", "");
    }
}
