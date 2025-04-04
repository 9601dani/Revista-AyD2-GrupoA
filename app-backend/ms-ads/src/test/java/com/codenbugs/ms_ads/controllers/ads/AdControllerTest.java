package com.codenbugs.ms_ads.controllers.ads;

import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;
import com.codenbugs.ms_ads.dto.response.CategoryResponseDTO;
import com.codenbugs.ms_ads.dto.response.LabelResponseDTO;
import com.codenbugs.ms_ads.services.ads.AdService;
import com.codenbugs.ms_ads.services.ads.AdTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdController.class)
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdTypeService adTypeService;

    @MockitoBean
    private AdService adService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllShouldReturnListOfAdTypes() throws Exception {
        AdTypeResponseDTO dto = new AdTypeResponseDTO(1, "Banner");
        Mockito.when(adTypeService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/ads/types"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(dto))));
    }

    @Test
    void createShouldReturnCreatedAd() throws Exception {
        // Arrange
        AdRequestDTO request = new AdRequestDTO(
                "Contenido de prueba",
                15,
                1,
                List.of("Tecnología"),
                List.of("Urgente"),
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        AdTypeResponseDTO adType = new AdTypeResponseDTO(1, "Banner");
        CategoryResponseDTO category = new CategoryResponseDTO(1, "Tecnología");
        LabelResponseDTO label = new LabelResponseDTO(1, "Urgente");

        AdResponseDTO response = new AdResponseDTO(
                1,
                "Contenido de prueba",
                0,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                adType,
                List.of(category),
                List.of(label)
        );

        MultipartFile file = new MockMultipartFile(
                "file",
                "ad.png",
                "image/png",
                "fake image content".getBytes()
        );

        Mockito.when(adService.save(any(AdRequestDTO.class), any(MultipartFile.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(multipart("/v1/ads")
                        .file((MockMultipartFile) file)
                        .param("content", request.content())
                        .param("period", String.valueOf(request.period()))
                        .param("adType", String.valueOf(request.adType()))
                        .param("categories", request.categories().toArray(new String[0]))
                        .param("labels", request.labels().toArray(new String[0]))
                        .param("userId", String.valueOf(request.userId()))
                        .param("start", request.start().toString())
                        .param("end", request.end().toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Contenido de prueba"))
                .andExpect(jsonPath("$.numberViews").value(0))
                .andExpect(jsonPath("$.adType.name").value("Banner"))
                .andExpect(jsonPath("$.categories[0].name").value("Tecnología"))
                .andExpect(jsonPath("$.labels[0].name").value("Urgente"));

        verify(adService).save(any(AdRequestDTO.class), any(MultipartFile.class));
    }


    @Test
    void findRandomByUserIdShouldReturnAd() throws Exception {
        AdTypeResponseDTO adType = new AdTypeResponseDTO(1, "Banner");
        CategoryResponseDTO category = new CategoryResponseDTO(1, "Noticias");
        LabelResponseDTO label = new LabelResponseDTO(1, "Urgente");

        AdResponseDTO response = new AdResponseDTO(
                1,
                "Título de anuncio",
                5,
                LocalDate.now(),
                LocalDate.now().plusDays(15),
                adType,
                List.of(category),
                List.of(label)
        );

        Mockito.when(adService.findRandomByUserId(1)).thenReturn(response);

        mockMvc.perform(get("/v1/ads/random/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Título de anuncio"))
                .andExpect(jsonPath("$.numberViews").value(5))
                .andExpect(jsonPath("$.adType.name").value("Banner"))
                .andExpect(jsonPath("$.categories[0].name").value("Noticias"))
                .andExpect(jsonPath("$.labels[0].name").value("Urgente"));
    }


    @Test
    void incrementViewNumberShouldReturnUpdatedAd() throws Exception {
        AdTypeResponseDTO adType = new AdTypeResponseDTO(2, "Sidebar");
        CategoryResponseDTO category = new CategoryResponseDTO(2, "Deportes");
        LabelResponseDTO label = new LabelResponseDTO(2, "Popular");

        AdResponseDTO response = new AdResponseDTO(
                2,
                "Anuncio actualizado",
                10,
                LocalDate.now(),
                LocalDate.now().plusDays(20),
                adType,
                List.of(category),
                List.of(label)
        );

        Mockito.when(adService.incrementViews(2)).thenReturn(response);

        mockMvc.perform(put("/v1/ads/views/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.content").value("Anuncio actualizado"))
                .andExpect(jsonPath("$.numberViews").value(10))
                .andExpect(jsonPath("$.adType.name").value("Sidebar"))
                .andExpect(jsonPath("$.categories[0].name").value("Deportes"))
                .andExpect(jsonPath("$.labels[0].name").value("Popular"));
    }

}
