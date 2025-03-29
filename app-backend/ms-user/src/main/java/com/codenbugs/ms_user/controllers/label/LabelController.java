package com.codenbugs.ms_user.controllers.label;

import com.codenbugs.ms_user.dtos.label.LabelRequestUserDto;
import com.codenbugs.ms_user.dtos.label.LabelResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.repositories.label.LabelRepository;
import com.codenbugs.ms_user.services.labels.UserHasLabelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users/labels")
@AllArgsConstructor
public class LabelController {

    private UserHasLabelService userHasLabelService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveLabelsForUser (@RequestBody LabelRequestUserDto request) throws UserNotFoundException {
        Map<String, String> response = this.userHasLabelService.saveLabelsForUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{fkUser}")
    public ResponseEntity<List<LabelResponseDto>> getAllLabelsForUser(@PathVariable Integer fkUser) {
        List<LabelResponseDto> response = this.userHasLabelService.getLabelsForUser(fkUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LabelResponseDto>> getAllLabels() {
        List<LabelResponseDto> response = this.userHasLabelService.getAllLabels();
        return ResponseEntity.ok(response);
    }
}
