package com.codenbugs.ms_user.service.magazine;

import com.codenbugs.ms_user.exceptions.DocumentNotSaveException;
import com.codenbugs.ms_user.exceptions.documents.DocumentNotFoundException;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.repositories.magazine.DocumentRepository;
import com.codenbugs.ms_user.services.magazine.DocumentService;
import com.codenbugs.ms_user.services.magazine.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void saveDocumentShouldUpdateIfExists() {
        Document existing = new Document();
        existing.setId(1);
        existing.setPath("path/to/doc");
        existing.setMagazine(new Magazine());

        Document updated = new Document();
        updated.setId(1);
        updated.setPath("path/to/doc");
        updated.setMagazine(new Magazine());

        when(documentRepository.findByPath("path/to/doc")).thenReturn(Optional.of(existing));
        when(documentRepository.save(any(Document.class))).thenReturn(updated);

        Document result = documentService.saveDocument(existing);

        assertEquals("path/to/doc", result.getPath());
        verify(documentRepository).save(any(Document.class));
    }


    @Test
    void saveDocumentShouldCreateIfNotExists() throws DocumentNotSaveException {
        // Arrange
        Document inputDoc = new Document();
        inputDoc.setPath("new/path.pdf");
        inputDoc.setMagazine(new Magazine());

        when(documentRepository.findByPath("new/path.pdf")).thenReturn(Optional.empty());
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Document result = documentService.saveDocument(inputDoc);

        // Assert
        assertEquals("new/path.pdf", result.getPath());
        verify(documentRepository).save(any(Document.class));
    }

    @Test
    void getDocumentsShouldReturnList() {
        // Arrange
        int magazineId = 1;
        Document doc = new Document();
        when(documentRepository.findAllByMagazine_Id(magazineId)).thenReturn(List.of(doc));

        // Act
        List<Document> result = documentService.getDocuments(magazineId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getDocumentsShouldThrowIfEmpty() {
        // Arrange
        int magazineId = 99;
        when(documentRepository.findAllByMagazine_Id(magazineId)).thenReturn(Collections.emptyList());

        // Act + Assert
        assertThrows(DocumentNotFoundException.class, () -> documentService.getDocuments(magazineId));
    }
}
