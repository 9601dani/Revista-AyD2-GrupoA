package com.codenbugs.ms_user.dtos.report;

import java.time.LocalDate;

public record PaymentReportRequestDto(
        LocalDate startDate,
        LocalDate endDate,
        Integer magazineId // puede ser null
) {}
