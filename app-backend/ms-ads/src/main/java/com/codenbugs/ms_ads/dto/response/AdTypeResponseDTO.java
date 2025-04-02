package com.codenbugs.ms_ads.dto.response;

import com.codenbugs.ms_ads.models.ads.AdType;

public record AdTypeResponseDTO(
        Integer id,
        String name
) {
    public AdTypeResponseDTO(AdType adType) {
        this(adType.getId(), adType.getName());
    }
}
