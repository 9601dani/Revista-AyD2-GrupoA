package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.exceptions.DocumentNotSaveException;
import com.codenbugs.ms_user.models.magazine.Document;

import java.util.List;

public interface DocumentService {
    Document saveDocument(Document document) throws DocumentNotSaveException;
    List<Document> getDocuments(Integer FK_Magazine);
}
