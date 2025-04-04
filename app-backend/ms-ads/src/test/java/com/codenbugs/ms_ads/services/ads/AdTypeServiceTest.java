package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;
import com.codenbugs.ms_ads.models.ads.AdType;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdTypeServiceTest {

    @Mock
    private AdTypeRepository adTypeRepository;

    @InjectMocks
    private AdTypeServiceImpl adTypeService;

    @Test
    void findAllShouldReturnAdTypeDTOList() {
        // Arrange
        AdType adType1 = new AdType();
        adType1.setId(1);
        adType1.setName("IMAGE");

        AdType adType2 = new AdType();
        adType2.setId(2);
        adType2.setName("VIDEO");

        when(adTypeRepository.findAll()).thenReturn(List.of(adType1, adType2));

        // Act
        List<AdTypeResponseDTO> result = adTypeService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("IMAGE", result.get(0).name());
        assertEquals("VIDEO", result.get(1).name());

        verify(adTypeRepository).findAll();
    }
}
