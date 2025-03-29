package com.codenbugs.ms_email.services;

import com.codenbugs.ms_email.dto.EmailRequest;
import com.codenbugs.ms_email.exceptions.EmailException;
import com.codenbugs.ms_email.utils.MimeMessageHelperFactory;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final MimeMessageHelperFactory helperFactory;

    public String sendEmail(EmailRequest emailRequest) throws EmailException {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = this.helperFactory.create(mimeMessage, true, "UTF-8");
            helper.setTo(emailRequest.to());
            helper.setSubject(emailRequest.subject());
            helper.setText(emailRequest.content(), true);

            mailSender.send(mimeMessage);
            return "El correo se envió exitosamente!";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new EmailException("No se pudo enviar el correo.");
        }
    }
}
