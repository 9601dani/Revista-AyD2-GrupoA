package com.codenbugs.ms_user.dtos.user;

import com.codenbugs.ms_user.models.user.User;

public record UserReponseDto(
        Integer id,
        String username,
        String email,
        String token
) {
    public UserReponseDto(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getAuthToken());
    }
}
