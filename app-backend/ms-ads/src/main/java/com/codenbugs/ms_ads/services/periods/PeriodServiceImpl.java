package com.codenbugs.ms_ads.services.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import com.codenbugs.ms_ads.repositories.periods.PeriodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;

    @Override
    public List<PeriodResponseDTO> findAll() {
        return this.periodRepository.findAll().stream()
                .map(PeriodResponseDTO::new)
                .toList();
    }
}
