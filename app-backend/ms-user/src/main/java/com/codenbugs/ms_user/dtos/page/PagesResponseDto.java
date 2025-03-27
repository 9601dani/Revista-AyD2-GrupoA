package com.codenbugs.ms_user.dtos.page;

import com.codenbugs.ms_user.models.page.Page;

public record PagesResponseDto(Integer id, String name, String path, Boolean isEnabled) {

    public PagesResponseDto(Page page) {
        this(page.getId(), page.getName(), page.getPath(), page.getIsEnabled());
    }
}
