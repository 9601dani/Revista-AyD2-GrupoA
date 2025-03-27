package com.codenbugs.ms_email.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;

class MimeMessageHelperFactoryTest {

    @InjectMocks
    private MimeMessageHelperFactory mimeMessageHelperFactory;

    @Mock
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() throws MessagingException {
        MimeMessageHelper helper = this.mimeMessageHelperFactory.create(mimeMessage, true, "UTF-8");
        assertNotNull(helper);
    }
}