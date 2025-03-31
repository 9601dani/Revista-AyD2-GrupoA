package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.models.magazine.Category;

public record CategoryResponse(Integer id, String name) {

    public CategoryResponse(Category category){
        this(category.getId(), category.getName());
    }
}
