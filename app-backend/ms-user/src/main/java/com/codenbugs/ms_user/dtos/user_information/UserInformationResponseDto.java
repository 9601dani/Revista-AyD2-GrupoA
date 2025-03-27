package com.codenbugs.ms_user.dtos.user_information;

import com.codenbugs.ms_user.models.user_information.UserHasInformation;

import java.math.BigDecimal;

public record UserInformationResponseDto(
        Integer id,
        String photo_path,
        String name,
        Integer age,
        String description,
        BigDecimal current_balance,
        Integer fkUser
) {

    public UserInformationResponseDto(UserHasInformation uhi) {
        this(uhi.getId(), uhi.getPhoto_path(), uhi.getName(), uhi.getAge(), uhi.getDescription(), uhi.getCurrent_balance(), uhi.getUser().getId());
    }
}
