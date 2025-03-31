package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MagazineWithDocumentsResponse(
        Integer id,
        String name,
        Integer FK_User,
        String description,
        Boolean canComment,
        Boolean canLike,
        Boolean canSubscribe,
        MagazineType type,
        BigDecimal price,
        Boolean isEnabled,
        LocalDateTime dateCreated,
        List<DocumentResponse> documents
) {
    public MagazineWithDocumentsResponse(Magazine magazine) {
        this(
                magazine.getId(),
                magazine.getName(),
                magazine.getUser().getId(),
                magazine.getDescription(),
                magazine.getCanComment(),
                magazine.isCanLike(),
                magazine.isCanSubscribe(),
                magazine.getType(),
                magazine.getPrice(),
                magazine.isEnabled(),
                magazine.getDateCreated(),
                magazine.getDocuments().stream()
                        .map(DocumentResponse::new)
                        .collect(Collectors.toList())
        );
    }

}
