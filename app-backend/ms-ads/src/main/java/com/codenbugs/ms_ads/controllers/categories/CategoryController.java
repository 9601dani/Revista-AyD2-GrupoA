package com.codenbugs.ms_ads.controllers.categories;

import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;
import com.codenbugs.ms_ads.services.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/temp-categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        List<CategoryResponseDTO> categories = this.categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
