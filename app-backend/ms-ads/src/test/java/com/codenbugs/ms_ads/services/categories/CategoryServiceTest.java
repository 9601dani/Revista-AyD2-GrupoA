package com.codenbugs.ms_ads.services.categories;

import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;
import com.codenbugs.ms_ads.models.categories.Category;
import com.codenbugs.ms_ads.repositories.categories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAllShouldReturnCategoryDTOList() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Tech");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Science");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        // Act
        List<CategoryResponseDTO> result = categoryService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tech", result.get(0).name());
        assertEquals("Science", result.get(1).name());

        verify(categoryRepository).findAll();
    }
}
