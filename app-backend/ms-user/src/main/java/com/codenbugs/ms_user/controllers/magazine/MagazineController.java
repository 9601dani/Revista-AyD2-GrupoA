package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineWithDocumentsResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.services.magazine.MagazineService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/save")
    public ResponseEntity<MagazineResponse> saveMagazine(@RequestPart("magazine") MagazineRequest magazineRequest,
                                                         @RequestPart("file") MultipartFile file) throws UserNotFoundException {
        MagazineResponse magazineDTO = this.magazineService.saveMagazine(magazineRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(magazineDTO);
    }

    @GetMapping("/getByIdUser/{id}")
    public ResponseEntity<List<MagazineWithDocumentsResponse>> getMagazine(@PathVariable("id") Integer id) throws UserNotFoundException {
        List<MagazineWithDocumentsResponse> magazineDTO = this.magazineService.getByUserId(id);

        return ResponseEntity.ok(magazineDTO);
    }

}
