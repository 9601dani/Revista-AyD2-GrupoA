package com.codenbugs.ms_user.dtos.report;

import java.math.BigDecimal;

public record TopLikedMagazineDto(
        Integer magazineId,
        String magazineName,
        String magazineDescription,
        BigDecimal price,
        Long totalLikes
) {}
