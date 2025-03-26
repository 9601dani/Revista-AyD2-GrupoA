package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.exceptions.DocumentNotSaveException;
import com.codenbugs.ms_user.models.magazine.Document;

public interface DocumentService {
    Document saveDocument(Document document) throws DocumentNotSaveException;
}
