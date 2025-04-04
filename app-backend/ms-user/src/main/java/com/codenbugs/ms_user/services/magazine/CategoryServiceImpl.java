package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.response.CategoryResponse;
import com.codenbugs.ms_user.models.magazine.Category;
import com.codenbugs.ms_user.repositories.magazine.CategoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::new).collect(Collectors.toList());
    }

    @Override
    public Category findByName(String name) {
        Category c = categoryRepository.findByName(name);

        if(c == null){
            c = new Category();
            c.setName(name.toLowerCase());
            c = this.categoryRepository.save(c);
        }
        return c;
    }

}
