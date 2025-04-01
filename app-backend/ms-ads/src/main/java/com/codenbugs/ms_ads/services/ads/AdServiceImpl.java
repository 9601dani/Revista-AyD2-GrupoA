package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.clients.UploadRestClient;
import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.exceptions.NotSavedException;
import com.codenbugs.ms_ads.models.ads.AdType;
import com.codenbugs.ms_ads.repositories.ads.AdRepository;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import com.codenbugs.ms_ads.repositories.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {

    private final UploadRestClient uploadRestClient;
    private final AdRepository adRepository;
    private final AdTypeRepository adTypeRepository;
    private final CategoryRepository categoryRepository;
    private final LabelRepository labelRepository;

    @Override
    public AdResponseDTO save(AdRequestDTO adRequestDTO, MultipartFile file) throws NotSavedException {

        Optional<AdType> adTypeOptional = this.adTypeRepository.findById(adRequestDTO.adType());
        if(adTypeOptional.isEmpty()) {
            throw new NotSavedException("The ad could not be saved.");
        }


        return null;
    }

    @Override
    public AdResponseDTO update(AdRequestDTO adRequestDTO, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public AdResponseDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<AdResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public List<AdResponseDTO> findByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public AdResponseDTO findRandomByUserId(Integer userId) {
        return null;
    }
}
