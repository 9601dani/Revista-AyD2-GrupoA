package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.exceptions.NotSavedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdService {

    AdResponseDTO save(AdRequestDTO adRequestDTO, MultipartFile file) throws NotSavedException;
    AdResponseDTO update(AdRequestDTO adRequestDTO, MultipartFile file);
    void delete(Integer id);
    AdResponseDTO findById(Integer id);
    List<AdResponseDTO> findAll();
    List<AdResponseDTO> findByUserId(Integer userId);
    AdResponseDTO findRandomByUserId(Integer userId);
}
