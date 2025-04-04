package com.codenbugs.ms_user.dtos.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentReportDto(
        Integer magazineId,
        String magazineName,
        LocalDate dateCreated,
        BigDecimal pay
) {}
