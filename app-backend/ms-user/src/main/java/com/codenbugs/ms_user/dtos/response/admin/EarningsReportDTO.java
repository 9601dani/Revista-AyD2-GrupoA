package com.codenbugs.ms_user.dtos.response.admin;

import java.math.BigDecimal;

public record EarningsReportDTO (
        Integer magazineId,
        String magazineName,
        BigDecimal totalAdCost,
        BigDecimal totalIncome,
        BigDecimal totalProfit
) {}

