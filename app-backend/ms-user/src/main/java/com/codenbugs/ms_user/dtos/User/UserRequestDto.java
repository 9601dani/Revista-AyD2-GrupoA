package com.codenbugs.ms_user.dtos.User;


public record UserRequestDto(
        String email,
        String username,
        String password
) {
}
