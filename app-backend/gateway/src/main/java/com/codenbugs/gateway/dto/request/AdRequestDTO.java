package com.codenbugs.gateway.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdRequestDTO(
        @NotBlank String name,
        @NotBlank String content,
        @NotNull Integer periodId,
        @NotNull Integer adTypeId,
        @NotNull LocalDate dateCreated,
        @NotNull LocalDate dateEnded) {
}
