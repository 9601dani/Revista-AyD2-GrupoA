package com.codenbugs.ms_ads.services.categories;

import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();
}
