package com.codenbugs.gateway.services.ads;

import com.codenbugs.gateway.dto.request.AdRequestDTO;
import com.codenbugs.gateway.dto.response.AdResponseDTO;

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
