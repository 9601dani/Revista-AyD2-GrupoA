package com.codenbugs.ms_ads.dto.response;

import com.codenbugs.ms_ads.models.categories.Category;

public record CategoryResponseDTO(
        Integer id,
        String name
) {

    public CategoryResponseDTO(Category category) {
        this(category.getId(), category.getName());
    }
}
