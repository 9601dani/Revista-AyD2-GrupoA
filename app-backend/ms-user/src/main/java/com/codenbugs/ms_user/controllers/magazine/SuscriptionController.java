package com.codenbugs.ms_user.controllers.magazine;

import com.codenbugs.ms_user.dtos.report.*;
import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.suscription.*;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Suscription;
import com.codenbugs.ms_user.services.magazine.SuscriptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/all/{fkUser}")
    public ResponseEntity<List<AllSuscriptionResponseDto>> getSuscriptionsWithMagazineByUserId(@PathVariable("fkUser") Integer fkUser) {
        return ResponseEntity.ok(this.suscriptionService.getSuscriptionsWithMagazineByUserId(fkUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllSuscriptionResponseDto> getSuscriptionById(@PathVariable("id") Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(this.suscriptionService.getSuscriptionById(id));
    }

    @PutMapping("/update/like")
    public ResponseEntity<SuscriptionResponseDto> updateIsLike(@RequestBody SuscriptionLikeRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(this.suscriptionService.updateIsLike(request));
    }

    @PostMapping("/comment/save")
    public ResponseEntity<CommentMagazineResponse> saveComment(@RequestBody CommentRequest request) throws UserNotFoundException {
        CommentMagazineResponse commentMagazineResponse = suscriptionService.saveComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMagazineResponse);
    }

    @PostMapping("/comment/report1")
    public List<CommentReportDto> getCommentsReport(@RequestBody CommentReportRequestDto request) {
        return suscriptionService.getCommentReport(request);
    }

    @PostMapping("/report/report2")
    public List<SuscriptionReportDto> getSuscriptionReport(@RequestBody SuscriptionReportRequestDto request) {
        return suscriptionService.getReport(request);
    }

    @PostMapping("/report/top-liked")
    public List<TopLikedMagazineDto> getTopLiked(@RequestBody TopLikedRequestDto request) {
        return suscriptionService.getTopLikedMagazines(request);
    }

    @PostMapping("/report/payments")
    public List<PaymentReportDto> getPaymentReport(@RequestBody PaymentReportRequestDto request) {
        return suscriptionService.getPaymentReport(request);
    }
}
