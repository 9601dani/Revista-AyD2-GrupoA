package com.codenbugs.ms_user.dtos.suscription;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SuscriptionLikeRequest(
        Integer id,
        Boolean isLike
) {
}
