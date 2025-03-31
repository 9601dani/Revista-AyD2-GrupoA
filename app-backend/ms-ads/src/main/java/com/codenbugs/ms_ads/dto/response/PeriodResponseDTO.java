package com.codenbugs.ms_ads.dto.response;

import com.codenbugs.ms_ads.models.periods.Period;

import java.math.BigDecimal;

public record PeriodResponseDTO(
        Integer id,
        String name,
        BigDecimal cost
) {

    public PeriodResponseDTO(Period period) {
        this(period.getId(), period.getName(), period.getCost());
    }
}
