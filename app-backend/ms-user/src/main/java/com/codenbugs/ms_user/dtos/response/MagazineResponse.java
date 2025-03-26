package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.magazine.Magazine;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record MagazineResponse(
        Integer id,
        String name,
        Integer FK_User,
        String description,
        Boolean canComment,
        Boolean canLike,
        Boolean canSubscribe,
        MagazineType type,
        BigDecimal price,
        Boolean isEnabled,
        LocalDateTime dateCreated
) {

    public MagazineResponse(Magazine magazine){
        this(magazine.getId(), magazine.getName(), magazine.getUser().getId(),
                magazine.getDescription(), magazine.getCanComment(),
                magazine.isCanLike(), magazine.isCanSubscribe(),
                magazine.getType(), magazine.getPrice(), magazine.isEnabled(),
                magazine.getDateCreated());
    }
}
