package com.codenbugs.ms_email.controllers;

import com.codenbugs.ms_email.dto.EmailRequest;
import com.codenbugs.ms_email.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSendEmail() throws Exception {

        EmailRequest emailRequest = new EmailRequest("to@test.com", "Subject", "content");
        String requestJson = "{\"to\":\"to@test.com\",\"subject\":\"Subject\",\"content\":\"content\"}";

        when(this.emailService.sendEmail(emailRequest)).thenReturn("El correo se envió exitosamente!");

        mockMvc.perform(post("/v1/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"El correo se envió exitosamente!\"}"));
    }
}