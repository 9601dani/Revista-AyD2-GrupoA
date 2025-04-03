package com.codenbugs.ms_user.dtos.user;

import com.codenbugs.ms_user.models.user.User;

public record AuthorResponse(Integer id, String username, String email) {
    public AuthorResponse(User user) {

        this(user.getId(), user.getUsername(), user.getEmail());
    }
}
