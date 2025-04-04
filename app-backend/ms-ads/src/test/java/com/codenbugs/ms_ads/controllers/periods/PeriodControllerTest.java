package com.codenbugs.ms_ads.controllers.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;
import com.codenbugs.ms_ads.models.periods.Period;
import com.codenbugs.ms_ads.services.periods.PeriodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeriodController.class)
class PeriodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PeriodService periodService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllShouldReturnListOfPeriods() throws Exception {
        List<PeriodResponseDTO> mockPeriods = List.of(
                new PeriodResponseDTO(1, "ONEWEEK", new BigDecimal(7)),
                new PeriodResponseDTO(2, "TWOWEEKS", new BigDecimal(30))
        );

        Mockito.when(periodService.findAll()).thenReturn(mockPeriods);

        mockMvc.perform(get("/v1/periods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("ONEWEEK"))
                .andExpect(jsonPath("$[0].cost").value(7));
    }

    @Test
    void saveAllShouldReturnSavedPeriods() throws Exception {
        Period p1 = new Period();
        p1.setName("ONEWEEK");
        p1.setCost(new BigDecimal(90));

        Period p2 = new Period();
        p2.setName("TWOWEEKS");
        p2.setCost( new BigDecimal(365));

        List<Period> input = List.of(p1, p2);

        List<PeriodResponseDTO> saved = List.of(
                new PeriodResponseDTO(1, "ONEWEEK",  new BigDecimal(90)),
                new PeriodResponseDTO(2, "TWOWEEKS",  new BigDecimal(365))
        );

        Mockito.when(periodService.saveAll(anyList())).thenReturn(saved);

        mockMvc.perform(post("/v1/periods/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("ONEWEEK"))
                .andExpect(jsonPath("$[1].cost").value(365));
    }

}
