package com.codenbugs.ms_ads.services.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;

import java.util.List;

public interface PeriodService {

    List<PeriodResponseDTO> findAll();
}
