package com.codenbugs.ms_user.dtos.report;

import java.time.LocalDate;

public record TopLikedRequestDto(
        LocalDate startDate,
        LocalDate endDate
) {}
