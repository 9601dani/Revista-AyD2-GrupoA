package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdvertiserEarningsDTO(
        Integer userId,
        String username,
        Integer adId,
        Date dateCreated,
        String adType,
        String period,
        BigDecimal cost,
        Integer numberViews,
        Boolean isEnabled
) {

}
