package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.models.labels.Label;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabelDTOTest {

    @Test
    void constructorWithLabelShouldMapFieldsCorrectly() {
        // Arrange
        Label label = new Label();
        label.setId(1);
        label.setName("Tecnología");

        // Act
        LabelDTO dto = new LabelDTO(label);

        // Assert
        assertEquals(1, dto.id());
        assertEquals("Tecnología", dto.name());
    }
}
