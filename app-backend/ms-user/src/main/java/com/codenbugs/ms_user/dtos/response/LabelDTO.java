package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.models.labels.Label;

public record LabelDTO(
        Integer id,
        String name
) {
    public LabelDTO(Label label){
        this(label.getId(), label.getName());
    }
}
