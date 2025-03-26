package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.services.magazine.MagazineService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/magazines")
@RequiredArgsConstructor
@Getter
@Setter
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("")
    public String hello() {
        return "Hello World from Magazine!";
    }

    @PostMapping("/save/{path}")
    public ResponseEntity<MagazineResponse> saveMagazine(@RequestBody MagazineRequest magazineRequest) throws UserNotFoundException {
        MagazineResponse magazineDTO = this.magazineService.saveMagazine(magazineRequest, magazineRequest.path());
        return ResponseEntity.status(HttpStatus.CREATED).body(magazineDTO);
    }

}
