package com.codenbugs.ms_user.dtos.suscription;

public record CommentRequest(
        Integer fkSuscription,
        Integer fkMagazine,
        String content
) {
}
