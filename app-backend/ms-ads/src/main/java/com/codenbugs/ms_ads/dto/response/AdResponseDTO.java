package com.codenbugs.ms_ads.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AdResponseDTO(
        Integer id,
        String content,
        String path,
        Integer numberViews,
        LocalDateTime dateCreated,
        LocalDateTime dateEnded,
        AdTypeResponseDTO adType,
        List<CategoryResponseDTO> categories,
        List<LabelResponseDTO> labels
) {
}
