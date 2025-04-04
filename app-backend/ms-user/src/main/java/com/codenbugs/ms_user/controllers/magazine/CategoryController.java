package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.response.CategoryResponse;
import com.codenbugs.ms_user.services.magazine.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
@Getter
@Setter
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public String hello() {
        return "Hello World from categories!";
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> all() {
        return ResponseEntity.ok(this.categoryService.findAll());
    }


}
