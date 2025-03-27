package com.codenbugs.ms_user.dtos.module;

import com.codenbugs.ms_user.dtos.page.PagesResponseDto;
import com.codenbugs.ms_user.models.module.Module;

import java.util.List;

public record ModuleResponseDto(String name, List<PagesResponseDto> pages) {

    public ModuleResponseDto(Module module) {
        this(module.getName(), module.getPages().stream()
                .map(page -> new PagesResponseDto(page.getId(), page.getName(), module.getPath() + page.getPath(), page.getIsEnabled()))
                .toList());
    }

}
