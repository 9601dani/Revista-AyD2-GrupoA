package com.codenbugs.ms_user.controller.magazine;

import com.codenbugs.ms_user.controllers.magazine.CategoryController;
import com.codenbugs.ms_user.dtos.response.CategoryResponse;
import com.codenbugs.ms_user.services.magazine.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void hello_shouldReturnGreetingMessage() throws Exception {
        mockMvc.perform(get("/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    Assertions.assertThat(body).isEqualTo("Hello World from categories!");
                });
    }

    @Test
    void all_shouldReturnAllCategories() throws Exception {
        // Arrange
        CategoryResponse category = new CategoryResponse(1, "Test Category");
        List<CategoryResponse> expected = Collections.singletonList(category);
        when(categoryService.findAll()).thenReturn(expected);

        // Act
        mockMvc.perform(get("/v1/categories/all"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    Assertions.assertThat(body).isEqualTo(objectMapper.writeValueAsString(expected));
                });
    }

}
