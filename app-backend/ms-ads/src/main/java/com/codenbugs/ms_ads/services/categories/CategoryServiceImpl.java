package com.codenbugs.ms_ads.services.categories;

import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;
import com.codenbugs.ms_ads.repositories.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> findAll() {
        return this.categoryRepository.findAll().stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }
}
