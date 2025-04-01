package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionRequestDto;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Suscription;
import com.codenbugs.ms_user.services.magazine.SuscriptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/suscriptions")
@RequiredArgsConstructor
@Getter
@Setter
public class SuscriptionController {

    private final SuscriptionService suscriptionService;

    @PostMapping("/save")
    public ResponseEntity<SuscriptionResponseDto> saveSuscription(@RequestBody SuscriptionRequestDto request) throws UserNotFoundException {
        SuscriptionResponseDto suscriptionResponseDto = suscriptionService.saveSuscription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(suscriptionResponseDto);
    }

    @GetMapping("/user/{fkUser}")
    public ResponseEntity<List<SuscriptionResponseDto>> getSuscriptionsByUserId(@PathVariable("fkUser") Integer fkUser) {
        return ResponseEntity.ok(this.suscriptionService.getSuscriptionsByUserId(fkUser));
    }
}
