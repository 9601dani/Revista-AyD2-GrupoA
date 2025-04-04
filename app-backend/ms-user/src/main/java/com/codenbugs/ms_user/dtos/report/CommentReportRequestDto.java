package com.codenbugs.ms_user.dtos.report;

import java.time.LocalDateTime;

public record CommentReportRequestDto(
        LocalDateTime start,
        LocalDateTime end,
        Integer magazineId
) {}
