package com.codenbugs.ms_ads.services.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;
import com.codenbugs.ms_ads.models.periods.Period;
import com.codenbugs.ms_ads.repositories.periods.PeriodRepository;
import com.codenbugs.ms_ads.services.periods.PeriodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodServiceTest {

    @Mock
    private PeriodRepository periodRepository;

    @InjectMocks
    private PeriodServiceImpl periodService;

    @Test
    void findAll_shouldReturnListOfPeriodDTOs() {
        // Arrange
        Period p1 = new Period(); p1.setId(1); p1.setName("ONEWEEK"); p1.setCost(BigDecimal.TEN);
        Period p2 = new Period(); p2.setId(2); p2.setName("TWOWEEKS"); p2.setCost(BigDecimal.ONE);

        when(periodRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<PeriodResponseDTO> result = periodService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ONEWEEK", result.get(0).name());
        assertEquals("TWOWEEKS", result.get(1).name());

        verify(periodRepository).findAll();
    }

    @Test
    void saveAll_shouldSaveAndReturnDTOList() {
        // Arrange
        Period input1 = new Period(); input1.setId(1); input1.setName("ONEWEEK"); input1.setCost(BigDecimal.TEN);
        Period input2 = new Period(); input2.setId(2); input2.setName("TWOWEEKS"); input2.setCost(BigDecimal.ONE);

        when(periodRepository.save(input1)).thenReturn(input1);
        when(periodRepository.save(input2)).thenReturn(input2);

        // Act
        List<PeriodResponseDTO> result = periodService.saveAll(List.of(input1, input2));

        // Assert
        assertEquals(2, result.size());
        assertEquals("ONEWEEK", result.get(0).name());
        assertEquals("TWOWEEKS", result.get(1).name());

        verify(periodRepository).save(input1);
        verify(periodRepository).save(input2);
    }

    @Test
    void saveAll_shouldReturnEmptyListWhenInputIsEmpty() {
        List<PeriodResponseDTO> result = periodService.saveAll(List.of());
        assertTrue(result.isEmpty());
        verify(periodRepository, never()).save(any());
    }
}
