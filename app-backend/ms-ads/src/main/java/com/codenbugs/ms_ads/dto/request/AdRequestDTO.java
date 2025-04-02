package com.codenbugs.ms_ads.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdRequestDTO(
        String content,
        @NotNull Integer period,
        @NotNull Integer adType,
        @NotNull LocalDate start,
        @NotNull LocalDate end) {
}
