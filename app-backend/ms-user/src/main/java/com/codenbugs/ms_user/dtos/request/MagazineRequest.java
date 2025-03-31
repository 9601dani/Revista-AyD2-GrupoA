package com.codenbugs.ms_user.dtos.request;


import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.magazine.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record MagazineRequest(
        Integer id,
        @NotBlank String name,
        @NotBlank Integer FK_User,
        @NotBlank String description,
        Boolean canComment,
        Boolean canLike,
        Boolean canSubscribe,
        MagazineType type,
        @DecimalMin("0.00") BigDecimal price,
        Boolean isEnabled,
        String path,
        MultipartFile file,
        List<Label> labels,
        List<Category> categories
        ) {
}


