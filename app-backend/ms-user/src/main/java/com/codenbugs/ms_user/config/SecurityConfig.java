package com.codenbugs.ms_user.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder getEncoder() {

        String secret = System.getenv("SECRET_KEY");

        return new Pbkdf2PasswordEncoder(
                secret,
                1,
                32,
                SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }
}
