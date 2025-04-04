package com.codenbugs.ms_user.dtos.response.admin;

import java.util.List;

public record MagazineWithCommentsDTO(
        Integer magazineId,
        String magazineName,
        List<CommentDetailDTO> comments
) {}
