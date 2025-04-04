package com.codenbugs.ms_ads.services.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;
import com.codenbugs.ms_ads.models.periods.Period;

import java.util.List;

public interface PeriodService {

    List<PeriodResponseDTO> findAll();
    List<PeriodResponseDTO> saveAll(List<Period> periods);
}
