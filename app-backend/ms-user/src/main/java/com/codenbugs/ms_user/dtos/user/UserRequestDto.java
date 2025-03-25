package com.codenbugs.ms_user.dtos.user;


public record UserRequestDto(
        String email,
        String username,
        String password
) {
}
