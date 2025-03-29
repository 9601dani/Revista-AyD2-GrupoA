package com.codenbugs.ms_email.controllers;

import com.codenbugs.ms_email.dto.EmailRequest;
import com.codenbugs.ms_email.exceptions.EmailException;
import com.codenbugs.ms_email.services.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/emails")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<HashMap<String, String>> sendEmail(@RequestBody @Valid EmailRequest emailRequest) throws EmailException {

        String message = this.emailService.sendEmail(emailRequest);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}
