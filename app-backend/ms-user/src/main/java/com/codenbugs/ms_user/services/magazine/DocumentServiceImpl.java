package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.exceptions.DocumentNotSaveException;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.repositories.magazine.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document saveDocument(Document document) throws DocumentNotSaveException {
        Document savedDocument = new Document();

        Optional<Document> documentOptional = documentRepository.findByPath(document.getPath());

        if (documentOptional.isPresent()) {
            savedDocument.setId(documentOptional.get().getId());
            savedDocument.setPath(documentOptional.get().getPath());
            savedDocument.setMagazine(documentOptional.get().getMagazine());

            return this.documentRepository.save(savedDocument);
        }

        savedDocument.setPath(document.getPath());
        savedDocument.setMagazine(document.getMagazine());
        return this.documentRepository.save(savedDocument);
    }

}
