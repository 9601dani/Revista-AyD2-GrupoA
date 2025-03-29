package com.codenbugs.ms_user.dtos.user_information;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record UserInformationPhotoRequest(
        Integer fkUser,
        MultipartFile photo_path
) {
}
