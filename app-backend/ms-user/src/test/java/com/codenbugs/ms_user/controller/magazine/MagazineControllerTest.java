package com.codenbugs.ms_user.controller.magazine;

import com.codenbugs.ms_user.controllers.magazine.MagazineController;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineWithDocumentsResponse;
import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.services.magazine.MagazineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MagazineController.class)
class MagazineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MagazineService magazineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void helloShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/v1/magazines"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World from Magazine!"));
    }

    @Test
    void getAllMagazinesShouldReturnList() throws Exception {
        AllMagazineResponse response = new AllMagazineResponse(
                1, "Revista de Prueba", 1, "Descripción", true, true, true,
                MagazineType.FREE, BigDecimal.ZERO, true, LocalDateTime.now(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );
        when(magazineService.getAllMagazines()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/v1/magazines/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(response))));
    }

    @Test
    void getMagazineByIdShouldReturnMagazine() throws Exception {
        int id = 1;
        AllMagazineResponse response = new AllMagazineResponse(
                1, "Revista de Prueba", 1, "Descripción", true, true, true,
                MagazineType.FREE, BigDecimal.ZERO, true, LocalDateTime.now(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );
        when(magazineService.getMagazineById(id)).thenReturn(response);

        mockMvc.perform(get("/v1/magazines/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getMagazineByUserIdShouldReturnList() throws Exception {
        int id = 1;
        MagazineWithDocumentsResponse response = new MagazineWithDocumentsResponse(
                1, "Revista de Usuario", 1, "Descripción", true, true, true,
                MagazineType.FREE, BigDecimal.ZERO, true, LocalDateTime.now(),
                Collections.emptyList()
        );

        when(magazineService.getByUserId(id)).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/v1/magazines/getByUserId/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(response))));
    }

    @Test
    void saveMagazineShouldReturnCreated() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "file.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF content".getBytes());

        MagazineResponse response = new MagazineResponse(1, "Revista 1", 1, "Desc", true, true, true, MagazineType.FREE, BigDecimal.ZERO, true, LocalDateTime.now());
        when(magazineService.saveMagazine(any())).thenReturn(response);

        mockMvc.perform(multipart("/v1/magazines/save")
                        .file(file)
                        .param("name", "Revista 1")
                        .param("description", "Desc")
                        .param("FK_User", "1")
                        .param("canComment", "true")
                        .param("canLike", "true")
                        .param("canSubscribe", "true")
                        .param("type", "FREE")
                        .param("price", "0")
                        .param("isEnabled", "true"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMagazineShouldReturnOk() throws Exception {
        AllMagazineResponse response = new AllMagazineResponse(
                1, "Revista de Prueba", 1, "Descripción", true, true, true,
                MagazineType.FREE, BigDecimal.ZERO, true, LocalDateTime.now(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );
        when(magazineService.updateMagazine(org.mockito.ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(multipart("/v1/magazines/update")
                        .file(new MockMultipartFile("file", "file.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF content".getBytes()))
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .param("id", "1")
                        .param("name", "Revista 1")
                        .param("description", "Desc")
                        .param("FK_User", "1")
                        .param("canComment", "true")
                        .param("canLike", "true")
                        .param("canSubscribe", "true")
                        .param("type", "FREE")
                        .param("price", "0")
                        .param("isEnabled", "true"))
                .andExpect(status().isOk());
    }
}
