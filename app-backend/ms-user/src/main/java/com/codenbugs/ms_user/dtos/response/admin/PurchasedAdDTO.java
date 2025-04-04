package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public record PurchasedAdDTO (
        Integer adId,
        String user,
        String adType,
        String period,
        BigDecimal cost,
        Date dateCreated,
        Date dateEnded,
        Integer numberViews,
        Boolean isEnabled
) {}
