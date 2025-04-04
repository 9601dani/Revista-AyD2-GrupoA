package com.codenbugs.ms_user.dtos.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SuscriptionReportDto(
        Integer suscriptionId,
        LocalDate dateCreated,
        LocalDate dateEnded,
        boolean isLike,
        BigDecimal pay,
        Integer magazineId,
        String magazineName,
        String magazineDescription,
        BigDecimal magazinePrice
) {}
