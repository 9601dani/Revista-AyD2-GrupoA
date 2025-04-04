package com.codenbugs.ms_user.dtos.report;

import java.time.LocalDate;
import java.util.Optional;

public record SuscriptionReportRequestDto(
        LocalDate startDate,
        LocalDate endDate,
        Integer authorId,
        Integer magazineId
) {}
