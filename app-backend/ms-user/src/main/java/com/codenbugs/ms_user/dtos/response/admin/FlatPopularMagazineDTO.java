package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public record FlatPopularMagazineDTO(
        Integer magazineId,
        String magazineName,
        String subscriber,
        Date dateCreated,
        BigDecimal pay,
        Boolean isLike
) {
}

