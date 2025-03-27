package com.codenbugs.ms_email.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MimeMessageHelperFactory {

    public MimeMessageHelper create(MimeMessage mimeMessage, boolean multipart, String encoding) throws MessagingException {
        return new MimeMessageHelper(mimeMessage, multipart, encoding);
    }
}
