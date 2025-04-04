package com.codenbugs.ms_ads.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AdRequestDTO(
        String content,
        @NotNull Integer period,
        @NotNull Integer adType,
        List<String> categories,
        List<String> labels,
        @NotNull Integer userId,
        @NotNull LocalDate start,
        @NotNull LocalDate end) {
}
