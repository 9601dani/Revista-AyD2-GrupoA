package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.dtos.user.AuthorResponse;
import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.magazine.Magazine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AllMagazineResponse(
        Integer id,
        String name,
        AuthorResponse author,
        String description,
        Boolean canComment,
        Boolean canLike,
        Boolean canSubscribe,
        MagazineType type,
        BigDecimal price,
        Boolean isEnabled,
        LocalDateTime dateCreated,
        List<DocumentResponse> documents,
        List<LabelDTO> labels,
        List<CategoryResponse> categories
) {
    public AllMagazineResponse(Magazine magazine){
        this(magazine.getId(), magazine.getName(), new AuthorResponse(magazine.getUser()),
                magazine.getDescription(), magazine.getCanComment(),
                magazine.isCanLike(), magazine.isCanSubscribe(),
                magazine.getType(), magazine.getPrice(), magazine.isEnabled(),
                magazine.getDateCreated(),
                magazine.getDocuments() != null
                        ? magazine.getDocuments().stream().map(DocumentResponse::new).toList()
                        : List.of(),
                magazine.getLabels() != null
                        ? magazine.getLabels().stream().map(LabelDTO::new).toList()
                        : List.of(),
                magazine.getCategories() != null
                        ? magazine.getCategories().stream().map(CategoryResponse::new).toList()
                        : List.of());
    }
}
