package com.codenbugs.ms_user.dtos.request;


import com.codenbugs.ms_user.enums.MagazineType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record MagazineRequest(
        Integer id,
        @NotBlank String name,
        @NotBlank Integer FK_User,
        @NotBlank String description,
        Boolean canComment,
        Boolean canLike,
        Boolean canSubscribe,
        MagazineType type,
        @DecimalMin("0.00") BigDecimal price,
        Boolean isEnabled,
        String path
        ) {
}


