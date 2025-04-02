package com.codenbugs.ms_ads.dto.response;

import com.codenbugs.ms_ads.models.ads.Ad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AdResponseDTO(
        Integer id,
        String content,
        Integer numberViews,
        LocalDate dateCreated,
        LocalDate dateEnded,
        AdTypeResponseDTO adType,
        List<CategoryResponseDTO> categories,
        List<LabelResponseDTO> labels
) {

    public AdResponseDTO(Ad ad) {
        this(ad.getId(),
                ad.getContent(),
                ad.getNumberViews(),
                ad.getDateCreated(),
                ad.getDateEnded(),
                new AdTypeResponseDTO(ad.getAdType()),
                ad.getCategories().stream().map(CategoryResponseDTO::new).toList(),
                ad.getLabels().stream().map(LabelResponseDTO::new).toList());
    }
}
