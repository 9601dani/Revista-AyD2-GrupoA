package com.codenbugs.ms_user.dtos.suscription;

import com.codenbugs.ms_user.dtos.user.AuthorResponse;
import com.codenbugs.ms_user.models.magazine.Suscription;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SuscriptionResponseDto(
        Integer id,
        Boolean isLike,
        AuthorResponse user,
        Integer fkMagazine,
        LocalDate dateCreated,
        LocalDate dateEnded,
        Boolean isEnabled,
        BigDecimal pay
) {
    public SuscriptionResponseDto(Suscription suscription) {
        this(suscription.getId(),
                suscription.getIsLike(),
                new AuthorResponse(suscription.getUser()),
                suscription.getMagazine().getId(),
                suscription.getDateCreated(),
                suscription.getDateEnded(),
                suscription.getIsEnabled(),
                suscription.getPay()
        );
    }
}
