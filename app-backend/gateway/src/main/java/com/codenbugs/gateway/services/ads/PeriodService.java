package com.codenbugs.gateway.services.ads;

import com.codenbugs.gateway.dto.response.PeriodResponseDTO;

import java.util.List;

public interface PeriodService {

    List<PeriodResponseDTO> findAll();
}
