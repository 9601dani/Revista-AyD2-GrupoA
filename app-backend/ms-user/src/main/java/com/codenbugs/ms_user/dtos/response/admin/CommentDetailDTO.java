package com.codenbugs.ms_user.dtos.response.admin;

import java.sql.Date;
import java.time.LocalDateTime;

public record CommentDetailDTO(
        String commenter,
        String comment,
        Date dateCreated
) {}
