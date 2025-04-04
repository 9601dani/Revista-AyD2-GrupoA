package com.codenbugs.ms_user.service.magazine;

import com.codenbugs.ms_user.clients.UploadRestClient;
import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.*;
import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.magazine.*;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.MagazineHasCategoryRepository;
import com.codenbugs.ms_user.repositories.magazine.MagazineHasLabelRepository;
import com.codenbugs.ms_user.repositories.magazine.MagazineRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.services.labels.UserHasLabelServiceImpl;
import com.codenbugs.ms_user.services.magazine.CategoryService;
import com.codenbugs.ms_user.services.magazine.DocumentServiceImpl;
import com.codenbugs.ms_user.services.magazine.MagazineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MagazineServiceImplTest {

    @Mock
    private MagazineRepository magazineRepository;
    @Mock private UserRepository userRepository;
    @Mock private DocumentServiceImpl documentServiceImpl;
    @Mock private UploadRestClient uploadRestClient;
    @Mock private CategoryService categoryService;
    @Mock private UserHasLabelServiceImpl userHasLabelService;
    @Mock private MagazineHasCategoryRepository magazineHasCategory;
    @Mock private MagazineHasLabelRepository magazineHasLabel;

    @InjectMocks
    private MagazineServiceImpl magazineService;

    @Test
    void saveMagazineShouldSaveSuccessfully() throws Exception {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "doc.pdf", "application/pdf", "PDF content".getBytes());

        Label label = new Label();
        label.setId(1);
        label.setName("Tech");

        Category category = new Category();
        category.setId(1);
        category.setName("Science");

        MagazineRequest request = new MagazineRequest(
                null,
                "Revista Test",
                1,
                "Descripción",
                true,
                true,
                true,
                MagazineType.FREE,
                BigDecimal.ZERO,
                true,
                "path",
                file,
                List.of(label),
                List.of(category)
        );

        User user = new User();
        user.setId(1);

        Magazine savedMagazine = new Magazine();
        savedMagazine.setId(1);
        savedMagazine.setName("Revista Test");
        savedMagazine.setUser(user);

        HashMap<String, String> uploaded = new HashMap<>();
        uploaded.put("doc.pdf", "revistas/path/doc.pdf");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(uploadRestClient.uploadFile(file)).thenReturn(uploaded);
        when(magazineRepository.save(any(Magazine.class))).thenReturn(savedMagazine);
        when(categoryService.findByName("science")).thenReturn(category);
        when(userHasLabelService.findByName("Tech")).thenReturn(label);

        // Act
        MagazineResponse result = magazineService.saveMagazine(request);

        // Assert
        assertNotNull(result);
        assertEquals("Revista Test", result.name());
        assertEquals(1, result.id());

        verify(magazineRepository).save(any(Magazine.class));
        verify(documentServiceImpl).saveDocument(any(Document.class));
        verify(magazineHasCategory).save(any(MagazineHasCategory.class));
        verify(magazineHasLabel).save(any(MagazineHasLabel.class));
    }


    @Test
    void saveMagazineShouldThrowWhenUserNotFound() {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "doc.pdf", "application/pdf", "PDF".getBytes());

        MagazineRequest request = new MagazineRequest(
                null,
                "Revista",
                999,
                "Desc",
                true,
                true,
                true,
                MagazineType.FREE,
                BigDecimal.ZERO,
                true,
                "some/path",
                file,
                Collections.emptyList(),
                Collections.emptyList()
        );

        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> magazineService.saveMagazine(request));

        verifyNoInteractions(magazineRepository);
        verifyNoInteractions(uploadRestClient);
        verifyNoInteractions(documentServiceImpl);
    }


    @Test
    void saveMagazineShouldThrowWhenFileEmpty() {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "", "application/pdf", new byte[0]);

        MagazineRequest request = new MagazineRequest(
                null,
                "Revista",
                1,
                "Desc",
                true,
                true,
                true,
                MagazineType.FREE,
                BigDecimal.ZERO,
                true,
                "some/path",
                file,
                Collections.emptyList(),
                Collections.emptyList()
        );

        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> magazineService.saveMagazine(request));

        verifyNoInteractions(uploadRestClient);
        verifyNoInteractions(documentServiceImpl);
        verifyNoInteractions(magazineRepository);
    }

    @Test
    void getByUserIdShouldReturnList() throws Exception {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);

        Magazine magazine = new Magazine();
        magazine.setId(10);
        magazine.setName("Magazine 1");
        magazine.setUser(user);
        magazine.setDocuments(Collections.emptyList());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(magazineRepository.findByUserId(userId)).thenReturn(List.of(magazine));

        // Act
        List<MagazineWithDocumentsResponse> result = magazineService.getByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(magazine.getId(), result.get(0).id());
        verify(magazineRepository).findByUserId(userId);
    }


    @Test
    void getByUserId_shouldThrowWhenUserNotFound() {
        // Arrange
        int userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> magazineService.getByUserId(userId));
        verify(magazineRepository, never()).findByUserId(anyInt());
    }

    @Test
    void getAllMagazinesShouldReturnList() {
        // Arrange
        User user = new User();
        user.setId(1);

        Magazine magazine = new Magazine();
        magazine.setId(1);
        magazine.setName("Revista X");
        magazine.setUser(user);
        magazine.setDocuments(Collections.emptyList());
        magazine.setLabels(Collections.emptyList());
        magazine.setCategories(Collections.emptyList());

        when(magazineRepository.findAll()).thenReturn(List.of(magazine));

        // Act
        List<AllMagazineResponse> result = magazineService.getAllMagazines();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).id());
        assertEquals("Revista X", result.get(0).name());
        verify(magazineRepository).findAll();
    }


    @Test
    void getMagazineByIdShouldReturnMagazine() {
        // Arrange
        int magazineId = 1;
        Magazine magazine = new Magazine();
        magazine.setId(magazineId);
        magazine.setName("Revista Individual");

        User user = new User();
        user.setId(123);
        magazine.setUser(user);

        magazine.setDocuments(Collections.emptyList());
        magazine.setLabels(Collections.emptyList());
        magazine.setCategories(Collections.emptyList());

        when(magazineRepository.findById(magazineId)).thenReturn(magazine);

        // Act
        AllMagazineResponse result = magazineService.getMagazineById(magazineId);

        // Assert
        assertNotNull(result);
        assertEquals(magazineId, result.id());
        assertEquals("Revista Individual", result.name());
        verify(magazineRepository).findById(magazineId);
    }






}
