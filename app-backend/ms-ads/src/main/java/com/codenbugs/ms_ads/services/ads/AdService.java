package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;

import java.util.List;

public interface AdService {

    AdResponseDTO save(AdRequestDTO adRequestDTO);
    AdResponseDTO update(AdRequestDTO adRequestDTO);
    void delete(Integer id);
    AdResponseDTO findById(Integer id);
    List<AdResponseDTO> findAll();
    List<AdResponseDTO> findByUserId(Integer userId);
    AdResponseDTO findRandomByUserId(Integer userId);
}
