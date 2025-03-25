package com.codenbugs.ms_user.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record LoginRequestDto(String usernameOrEmail, String password) {
}
