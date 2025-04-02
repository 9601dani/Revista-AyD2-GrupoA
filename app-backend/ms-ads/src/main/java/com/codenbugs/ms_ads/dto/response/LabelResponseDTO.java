package com.codenbugs.ms_ads.dto.response;

import com.codenbugs.ms_ads.models.Label;

public record LabelResponseDTO(
        Integer id,
        String name
) {

    public LabelResponseDTO(Label label) {
        this(label.getId(), label.getName());
    }
}
