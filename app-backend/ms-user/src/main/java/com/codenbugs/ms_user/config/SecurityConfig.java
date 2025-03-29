package com.codenbugs.ms_user.config;

import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;


@Configuration
public class SecurityConfig {

    @Value("${password.secret}")
    private String secret;

    @Bean
    public PasswordEncoder getEncoder()  {
        return new Pbkdf2PasswordEncoder(
                secret,
                1,
                32,
                SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }
}
