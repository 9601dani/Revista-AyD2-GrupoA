package com.codenbugs.gateway.services.ads;

import com.codenbugs.gateway.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();
}
