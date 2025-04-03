package com.codenbugs.ms_ads.services.ads;

import com.codenbugs.ms_ads.clients.UploadRestClient;
import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.exceptions.NotFoundException;
import com.codenbugs.ms_ads.exceptions.NotSavedException;
import com.codenbugs.ms_ads.models.Label;
import com.codenbugs.ms_ads.models.ads.Ad;
import com.codenbugs.ms_ads.models.ads.AdType;
import com.codenbugs.ms_ads.models.categories.Category;
import com.codenbugs.ms_ads.models.periods.Period;
import com.codenbugs.ms_ads.models.users.User;
import com.codenbugs.ms_ads.repositories.ads.AdRepository;
import com.codenbugs.ms_ads.repositories.ads.AdTypeRepository;
import com.codenbugs.ms_ads.repositories.ads.LabelRepository;
import com.codenbugs.ms_ads.repositories.categories.CategoryRepository;
import com.codenbugs.ms_ads.repositories.periods.PeriodRepository;
import com.codenbugs.ms_ads.repositories.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {

    private final UploadRestClient uploadRestClient;
    private final AdRepository adRepository;
    private final AdTypeRepository adTypeRepository;
    private final CategoryRepository categoryRepository;
    private final LabelRepository labelRepository;
    private final PeriodRepository periodRepository;
    private final UserRepository userRepository;

    @Override
    public AdResponseDTO save(AdRequestDTO adRequestDTO, MultipartFile file) throws NotSavedException {

        AdType adType = this.adTypeRepository.findById(adRequestDTO.adType()).orElseThrow(() ->
                new NotSavedException("The ad could not be saved.")
        );

        List<Category> categories = new ArrayList<>();

        for(String categoryName : adRequestDTO.categories()) {
            Optional<Category> categoryOptional = this.categoryRepository.findByName(categoryName);
            if(categoryOptional.isEmpty()) {
                // TODO: SAVE NEW CATEGORY
                Category newCategory = new Category();
                newCategory.setName(categoryName);
                newCategory = this.categoryRepository.save(newCategory);
                categories.add(newCategory);
                continue;
            }
            Category category = categoryOptional.get();
            categories.add(category);
        }

        List<Label> labels = new ArrayList<>();
        for(String labelName : adRequestDTO.labels()) {
            Optional<Label> labelOptional = this.labelRepository.findByName(labelName);
            if(labelOptional.isEmpty()) {
                Label label = new Label();
                label.setName(labelName);
                label = this.labelRepository.save(label);
                labels.add(label);
                continue;
            }
            Label label = labelOptional.get();
            labels.add(label);
        }

        Map<String, String> result = new HashMap<>();
        boolean isImage = adType.getName().equals("IMAGE");
        if(isImage) {
            result = uploadRestClient.uploadImage(file);
        }

        Period period = this.periodRepository.findById(adRequestDTO.period()).orElseThrow(() ->
                new NotSavedException("Period not found.")
        );

        User user = this.userRepository.findById(adRequestDTO.userId()).orElseThrow(() -> new NotSavedException("User not found."));

        Ad ad = new Ad();
        ad.setAdType(adType);
        ad.setCategories(categories);
        ad.setPeriod(period);
        ad.setUser(user);
        ad.setLabels(labels);
        ad.setContent(isImage ? result.get("objectName") : adRequestDTO.content());
        ad.setDateCreated(adRequestDTO.start());
        ad.setDateEnded(adRequestDTO.end());

        Ad savedAd = this.adRepository.save(ad);

        return new AdResponseDTO(savedAd);
    }

    @Override
    public AdResponseDTO update(AdRequestDTO adRequestDTO, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public AdResponseDTO findById(Integer id) throws NotFoundException {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new NotFoundException(("Ad not found")));
        return new AdResponseDTO(ad);
    }

    @Override
    public List<AdResponseDTO> findAll() {
        return this.adRepository.findAll().stream()
                .map(AdResponseDTO::new)
                .toList();
    }

    @Override
    public List<AdResponseDTO> findByUserId(Integer userId) {
        return this.adRepository.findByUserId(userId).stream()
                .map(AdResponseDTO::new)
                .toList();
    }

    @Override
    public AdResponseDTO findRandomByUserId(Integer userId) throws NotFoundException {
        Optional<Ad> adOptional = this.adRepository.findRandomByUserId(userId);

        if(adOptional.isPresent()) {
            return new AdResponseDTO(adOptional.get());
        }

        Ad ad = this.adRepository.findRandom().orElseThrow(() -> new NotFoundException("Ad not found."));
        return new AdResponseDTO(ad);
    }

    @Override
    public AdResponseDTO incrementViews(Integer id) throws NotFoundException {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new NotFoundException(("Ad not found")));

        ad.setNumberViews(ad.getNumberViews() + 1);
        this.adRepository.save(ad);
        return new AdResponseDTO(ad);
    }
}
