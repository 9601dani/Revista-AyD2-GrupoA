package com.codenbugs.ms_user.dtos.label;

import com.codenbugs.ms_user.models.labels.Label;

import java.util.List;

public record LabelRequestUserDto(
        Integer fkUser,
        List<LabelRequestDto> labels
) {
}
