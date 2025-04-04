package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.models.magazine.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentResponseTest {

    @Test
    void constructorWithIdAndPathShouldSetFields() {
        // Act
        DocumentResponse response = new DocumentResponse(10, "bucket/docs/doc1.pdf");

        // Assert
        assertEquals(10, response.id());
        assertEquals("bucket/docs/doc1.pdf", response.path());
    }

    @Test
    void constructorWithDocumentShouldMapFieldsCorrectly() {
        // Arrange
        Document document = new Document();
        document.setId(15);
        document.setPath("bucket/docs/doc15.pdf");

        // Act
        DocumentResponse response = new DocumentResponse(document);

        // Assert
        assertEquals(15, response.id());
        assertEquals("bucket/docs/doc15.pdf", response.path());
    }
}
