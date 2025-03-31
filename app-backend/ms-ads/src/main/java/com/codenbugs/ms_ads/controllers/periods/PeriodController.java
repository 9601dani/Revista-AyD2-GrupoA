package com.codenbugs.ms_ads.controllers.periods;

import com.codenbugs.ms_ads.dto.response.PeriodResponseDTO;
import com.codenbugs.ms_ads.services.periods.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/periods")
@AllArgsConstructor
public class PeriodController {

    private final PeriodService periodService;

    @GetMapping()
    public ResponseEntity<List<PeriodResponseDTO>> findAll() {
        List<PeriodResponseDTO> periods = this.periodService.findAll();
        return ResponseEntity.ok(periods);
    }
}
