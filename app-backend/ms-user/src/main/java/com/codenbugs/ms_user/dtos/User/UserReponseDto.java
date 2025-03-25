package com.codenbugs.ms_user.dtos.User;

import com.codenbugs.ms_user.models.User.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
