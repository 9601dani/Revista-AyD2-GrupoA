package com.codenbugs.ms_user.dtos.report;

import java.time.LocalDateTime;

public record CommentReportDto(
        Integer commentId,
        String content,
        LocalDateTime dateCreated,
        Integer magazineId,
        String magazineName,
        String magazineDescription
) {}
