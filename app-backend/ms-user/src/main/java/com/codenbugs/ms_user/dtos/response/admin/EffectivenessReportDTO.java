package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public record EffectivenessReportDTO(
        Integer userId,
        String username,
        Integer adId,
        Date dateCreated,
        Date dateEnded,
        Integer numberViews,
        String adType,
        String periodName,
        BigDecimal adCost
) {
}
