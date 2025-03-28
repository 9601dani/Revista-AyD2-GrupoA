package com.codenbugs.ms_user.dtos.label;

public record LabelResponseDto (
        Integer id,
        Integer fkUser,
        Integer fkLabel,
        String label
) {
}
