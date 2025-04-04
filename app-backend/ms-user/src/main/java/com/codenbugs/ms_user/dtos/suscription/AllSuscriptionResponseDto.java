package com.codenbugs.ms_user.dtos.suscription;

import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.models.magazine.Suscription;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AllSuscriptionResponseDto(
        Integer id,
        Boolean isLike,
        Integer fkUser,
        AllMagazineResponse magazine,
        LocalDate dateCreated,
        LocalDate dateEnded,
        Boolean isEnabled,
        BigDecimal pay
) {
    public AllSuscriptionResponseDto(Suscription suscription) {
        this(suscription.getId(),
                suscription.getIsLike(),
                suscription.getUser().getId(),
                new AllMagazineResponse(suscription.getMagazine()),
                suscription.getDateCreated(),
                suscription.getDateEnded(),
                suscription.getIsEnabled(),
                suscription.getPay()
        );
    }
}
