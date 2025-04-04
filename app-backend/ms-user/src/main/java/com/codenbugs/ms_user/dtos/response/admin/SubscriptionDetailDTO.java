package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public record SubscriptionDetailDTO (
        String subscriber,
        Date dateCreated,
        BigDecimal pay,
        Boolean isLike
) {}
