package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
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
    public ResponseEntity<MagazineResponse> saveMagazine(@ModelAttribute MagazineRequest magazineRequest) throws UserNotFoundException {
        MagazineResponse magazineDTO = this.magazineService.saveMagazine(magazineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(magazineDTO);
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<List<MagazineWithDocumentsResponse>> getMagazine(@PathVariable("id") Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(this.magazineService.getByUserId(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AllMagazineResponse>> getAllMagazines() {
        return ResponseEntity.ok(this.magazineService.getAllMagazines());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AllMagazineResponse> getMagazineById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.magazineService.getMagazineById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<AllMagazineResponse> updateMagazine(@ModelAttribute MagazineRequest magazineRequest) throws UserNotFoundException {
        return ResponseEntity.ok(this.magazineService.updateMagazine(magazineRequest));
    }


}
