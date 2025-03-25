package com.codenbugs.ms_user.dtos;

import com.codenbugs.ms_user.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserReponseDto {

    private Integer id;
    private String username;
    private String email;

    public UserReponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
