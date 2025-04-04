package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.clients.UploadRestClient;
import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.exceptions.NotFoundException;
import com.codenbugs.ms_ads.models.Label;
import com.codenbugs.ms_ads.models.ads.Ad;
import com.codenbugs.ms_ads.models.ads.AdType;
import com.codenbugs.ms_ads.models.categories.Category;
import com.codenbugs.ms_ads.models.periods.Period;
import com.codenbugs.ms_ads.models.users.User;
import com.codenbugs.ms_ads.repositories.ads.AdRepository;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import com.codenbugs.ms_ads.repositories.ads.LabelRepository;
import com.codenbugs.ms_ads.repositories.categories.CategoryRepository;
import com.codenbugs.ms_ads.repositories.periods.PeriodRepository;
import com.codenbugs.ms_ads.repositories.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @Mock
    private UploadRestClient uploadRestClient;
    @Mock private AdRepository adRepository;
    @Mock private AdTypeRepository adTypeRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private LabelRepository labelRepository;
    @Mock private PeriodRepository periodRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private AdServiceImpl adService;

    private Ad createCompleteAd(int id) {
        Ad ad = new Ad();
        ad.setId(id);
        ad.setContent("Contenido");
        ad.setNumberViews(0);
        ad.setDateCreated(LocalDate.now());
        ad.setDateEnded(LocalDate.now().plusDays(5));

        AdType adType = new AdType();
        adType.setId(1);
        adType.setName("IMAGE");
        ad.setAdType(adType);

        Period period = new Period();
        period.setId(1);
        period.setName("Trimestral");
        period.setCost(BigDecimal.TEN);
        ad.setPeriod(period);

        User user = new User();
        user.setId(1);
        ad.setUser(user);

        Category cat = new Category();
        cat.setId(1);
        cat.setName("Tech");
        ad.setCategories(List.of(cat));

        Label label = new Label();
        label.setId(1);
        label.setName("Label");
        ad.setLabels(List.of(label));

        return ad;
    }

    @Test
    void saveShouldStoreAdWithImage() throws Exception {
        // Arrange
        AdType adType = new AdType();
        adType.setId(1);
        adType.setName("IMAGE");

        Period period = new Period();
        period.setId(1);
        period.setName("TWOWEEKS");
        period.setCost(BigDecimal.valueOf(90));

        User user = new User();
        user.setId(1);

        MultipartFile file = new MockMultipartFile("file", "img.jpg", "image/jpeg", new byte[0]);

        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("objectName", "image123.jpg");

        Ad savedAd = new Ad();
        savedAd.setId(1);
        savedAd.setAdType(adType);
        savedAd.setContent("image123.jpg");

        AdRequestDTO request = new AdRequestDTO(
                null,
                1,
                1,
                List.of("Cat"),
                List.of("Label"),
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(5)
        );

        Category newCategory = new Category();
        newCategory.setId(1);
        newCategory.setName("Cat");

        Label newLabel = new Label();
        newLabel.setId(1);
        newLabel.setName("Label");

        // Mocks
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(categoryRepository.findByName("Cat")).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(newCategory);
        when(labelRepository.findByName("Label")).thenReturn(Optional.empty());
        when(labelRepository.save(any())).thenReturn(newLabel);
        when(uploadRestClient.uploadImage(file)).thenReturn((HashMap<String, String>) uploadResult);
        when(periodRepository.findById(1)).thenReturn(Optional.of(period));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(adRepository.save(any())).thenReturn(savedAd);

        // Act
        AdResponseDTO result = adService.save(request, file);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.id());
    }



    @Test
    void findById_shouldReturnAd() throws NotFoundException {
        Ad ad = createCompleteAd(1);
        when(adRepository.findById(1)).thenReturn(Optional.of(ad));

        AdResponseDTO result = adService.findById(1);

        assertEquals(1, result.id());
    }

    @Test
    void findAll_shouldReturnList() {
        when(adRepository.findAll()).thenReturn(List.of(createCompleteAd(1), createCompleteAd(2)));

        List<AdResponseDTO> result = adService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findByUserId_shouldReturnList() {
        when(adRepository.findByUserId(1)).thenReturn(List.of(createCompleteAd(1)));

        List<AdResponseDTO> result = adService.findByUserId(1);

        assertEquals(1, result.size());
    }

    @Test
    void findRandomByUserId_shouldReturnUserAd() throws NotFoundException {
        Ad ad = createCompleteAd(1);
        when(adRepository.findRandomByUserId(1)).thenReturn(Optional.of(ad));

        AdResponseDTO result = adService.findRandomByUserId(1);

        assertEquals(1, result.id());
    }

    @Test
    void findRandomByUserId_shouldReturnRandomAd() throws NotFoundException {
        Ad ad = createCompleteAd(2);
        when(adRepository.findRandomByUserId(1)).thenReturn(Optional.empty());
        when(adRepository.findRandom()).thenReturn(Optional.of(ad));

        AdResponseDTO result = adService.findRandomByUserId(1);

        assertEquals(2, result.id());
    }

    @Test
    void incrementViews_shouldAddOneAndReturnAd() throws NotFoundException {
        Ad ad = createCompleteAd(1);
        ad.setNumberViews(5);

        when(adRepository.findById(1)).thenReturn(Optional.of(ad));
        when(adRepository.save(any())).thenReturn(ad);

        AdResponseDTO result = adService.incrementViews(1);

        assertEquals(6, result.numberViews());
    }
}
