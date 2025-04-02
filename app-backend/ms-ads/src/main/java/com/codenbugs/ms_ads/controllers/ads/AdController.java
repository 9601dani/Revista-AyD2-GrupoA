package com.codenbugs.ms_ads.controllers.ads;

import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;
import com.codenbugs.ms_ads.exceptions.NotSavedException;
import com.codenbugs.ms_ads.services.ads.AdService;
import com.codenbugs.ms_ads.services.ads.AdTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/ads")
@AllArgsConstructor
public class AdController {

    // private final AdService adService;
    private final AdTypeService adTypeService;
    private final AdService adService;

    @GetMapping("/types")
    public ResponseEntity<List<AdTypeResponseDTO>> findAll() {
        List<AdTypeResponseDTO> adTypes = this.adTypeService.findAll();
        return ResponseEntity.ok(adTypes);
    }

    @PostMapping()
    public ResponseEntity<AdResponseDTO> create(@ModelAttribute AdRequestDTO adRequestDTO, @RequestPart(value = "file", required = false) MultipartFile file) throws NotSavedException {
        AdResponseDTO adResponseDTO = this.adService.save(adRequestDTO, file);
        return ResponseEntity.ok(adResponseDTO);
    }

    @GetMapping("/random/{id}")
    public ResponseEntity<Map<String, String>> getRandomByUserId(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        response.put("received", String.valueOf(id));
        return ResponseEntity.ok(response);
    }
}
