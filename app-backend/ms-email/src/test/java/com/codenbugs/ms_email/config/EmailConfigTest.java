package com.codenbugs.ms_email.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

class EmailConfigTest {

    @InjectMocks
    private EmailConfig emailConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetMailSender() {
        JavaMailSender javaMailSender = this.emailConfig.getJavaMailSender();

        assertNotNull(javaMailSender);
    }
}