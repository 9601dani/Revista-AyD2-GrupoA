package com.codenbugs.ms_user.dtos.user_information;

public record UserInformationRequestDto(
        String name,
        Integer age,
        String description,
        Integer fkUser
) {
}
