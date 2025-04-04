package com.codenbugs.ms_user.service.magazine;

import com.codenbugs.ms_user.dtos.response.CategoryResponse;
import com.codenbugs.ms_user.models.magazine.Category;
import com.codenbugs.ms_user.repositories.magazine.CategoryRepository;
import com.codenbugs.ms_user.services.magazine.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAllShouldReturnCategoryResponses() {
        // Arrange
        Category cat1 = new Category();
        cat1.setId(1);
        cat1.setName("cat1");

        Category cat2 = new Category();
        cat2.setId(2);
        cat2.setName("cat2");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        // Act
        List<CategoryResponse> responses = categoryService.findAll();

        // Assert
        assertEquals(2, responses.size());
        assertEquals("cat1", responses.get(0).name());
        assertEquals("cat2", responses.get(1).name());
    }

    @Test
    void findByNameShouldReturnExistingCategory() {
        // Arrange
        Category existing = new Category();
        existing.setId(1);
        existing.setName("existing");

        when(categoryRepository.findByName("existing")).thenReturn(existing);

        // Act
        Category result = categoryService.findByName("existing");

        // Assert
        assertNotNull(result);
        assertEquals("existing", result.getName());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void findByNameShouldCreateNewIfNotFound() {
        // Arrange
        String name = "newCategory";
        when(categoryRepository.findByName(name)).thenReturn(null);

        Category saved = new Category();
        saved.setId(1);
        saved.setName(name.toLowerCase());

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        // Act
        Category result = categoryService.findByName(name);

        // Assert
        assertNotNull(result);
        assertEquals(name.toLowerCase(), result.getName());
        verify(categoryRepository).save(any(Category.class));
    }
}
