package com.codenbugs.ms_user.config;

import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

import java.security.NoSuchAlgorithmException;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder getEncoder() throws SettingNotFoundException {

        String secret = System.getenv("SECRET_KEY");
        if (secret == null) {
            throw new SettingNotFoundException("No existe la key del encoder");
        }

        return new Pbkdf2PasswordEncoder(
                secret,
                1,
                32,
                SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }
}
