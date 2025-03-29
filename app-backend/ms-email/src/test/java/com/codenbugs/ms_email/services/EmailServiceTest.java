package com.codenbugs.ms_email.services;

import com.codenbugs.ms_email.dto.EmailRequest;
import com.codenbugs.ms_email.exceptions.EmailException;
import com.codenbugs.ms_email.utils.MimeMessageHelperFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelperFactory helperFactory;

    private EmailRequest emailRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailRequest = new EmailRequest("to@gmail.com", "subject", "<html><body><h1>Hello World</h1></body></html>");
    }

    @Test
    void shouldSendEmail() throws MessagingException, EmailException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        when(this.mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(this.helperFactory.create(mimeMessage, true, "UTF-8")).thenReturn(helper);

        this.emailService.sendEmail(emailRequest);
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void shouldThrowEmailNotSendException() throws MessagingException {

        when(this.mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(this.helperFactory.create(mimeMessage, true, "UTF-8")).thenThrow(new MessagingException());

        Exception exception = assertThrows(EmailException.class, () -> {
            this.emailService.sendEmail(emailRequest);
        });

        String expectedMessage = "No se pudo enviar el correo.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}