package com.codenbugs.gateway.services.ads;

import com.codenbugs.gateway.dto.response.AdTypeResponseDTO;

import java.util.List;

public interface AdTypeService {

    List<AdTypeResponseDTO> findAll();
}
