package com.codenbugs.ms_user.dtos.response;


import com.codenbugs.ms_user.models.magazine.Document;

public record DocumentResponse(Integer id, String path) {
    public DocumentResponse(Document document){
        this(document.getId(), document.getPath());
    }

}

