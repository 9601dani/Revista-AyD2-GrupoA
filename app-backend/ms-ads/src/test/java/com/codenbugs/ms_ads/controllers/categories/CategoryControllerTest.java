package com.codenbugs.ms_ads.controllers.categories;

import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;
import com.codenbugs.ms_ads.services.categories.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void findAllShouldReturnListOfCategories() throws Exception {
        // Arrange
        List<CategoryResponseDTO> mockCategories = List.of(
                new CategoryResponseDTO(1, "Deportes"),
                new CategoryResponseDTO(2, "Noticias")
        );

        Mockito.when(categoryService.findAll()).thenReturn(mockCategories);

        mockMvc.perform(get("/v1/temp-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Deportes"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Noticias"));

        Mockito.verify(categoryService).findAll();
    }
}
