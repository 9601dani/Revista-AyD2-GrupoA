package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;

import java.util.List;

public interface AdTypeService {

    List<AdTypeResponseDTO> findAll();
}
