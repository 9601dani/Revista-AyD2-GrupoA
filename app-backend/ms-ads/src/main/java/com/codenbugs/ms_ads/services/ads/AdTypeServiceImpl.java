package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdTypeServiceImpl implements AdTypeService {

    private final AdTypeRepository adTypeRepository;

    @Override
    public List<AdTypeResponseDTO> findAll() {
        return this.adTypeRepository.findAll().stream()
                .map(AdTypeResponseDTO::new)
                .toList();
    }
}
