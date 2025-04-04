package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.response.CategoryResponse;
import com.codenbugs.ms_user.models.magazine.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();
    Category findByName(String name);
}
