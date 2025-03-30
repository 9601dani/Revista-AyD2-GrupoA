package com.codenbugs.gateway.dto.response;

import java.math.BigDecimal;

public record PeriodResponseDTO(
        Integer id,
        String name,
        BigDecimal cost
) {
}
