package com.codenbugs.ms_user.dtos.label;

import com.codenbugs.ms_user.models.labels.Label;

public record LabelResponseDto (
        Integer id,
        String name
) {
    public LabelResponseDto(Label label){this(label.getId(), label.getName());}
}
