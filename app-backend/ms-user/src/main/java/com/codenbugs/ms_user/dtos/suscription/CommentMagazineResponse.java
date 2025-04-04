package com.codenbugs.ms_user.dtos.suscription;

import com.codenbugs.ms_user.models.magazine.Comment;

import java.time.LocalDateTime;

public record CommentMagazineResponse(
         Integer id,
         SuscriptionResponseDto suscription,
         Integer fkMagazine,
         String content,
         LocalDateTime dateCreated
) {
    public CommentMagazineResponse(Comment comment) {
        this(comment.getId(), new SuscriptionResponseDto(comment.getSuscription()), comment.getMagazine().getId(), comment.getContent(), comment.getDateCreated());
    }
}
