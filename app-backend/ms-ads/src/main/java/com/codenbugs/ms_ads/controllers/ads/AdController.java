package com.codenbugs.ms_ads.controllers.ads;

import com.codenbugs.ms_ads.dto.request.AdRequestDTO;
import com.codenbugs.ms_ads.dto.response.AdResponseDTO;
import com.codenbugs.ms_ads.dto.response.AdTypeResponseDTO;
import com.codenbugs.ms_ads.services.ads.AdService;
import com.codenbugs.ms_ads.services.ads.AdTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/ads")
@AllArgsConstructor
public class AdController {

    // private final AdService adService;
    private final AdTypeService adTypeService;

    @GetMapping("/types")
    public ResponseEntity<List<AdTypeResponseDTO>> findAll() {
        List<AdTypeResponseDTO> adTypes = this.adTypeService.findAll();
        return ResponseEntity.ok(adTypes);
    }

    @PostMapping()
    public ResponseEntity<String> create(@ModelAttribute AdRequestDTO adRequestDTO, @RequestPart("file") MultipartFile file) {
        System.out.println(adRequestDTO);
        return ResponseEntity.ok("OK");
    }
}
