package com.codenbugs.ms_user.controllers.admin;

import com.codenbugs.ms_user.dtos.response.admin.*;
import com.codenbugs.ms_user.services.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/earnings")
    public ResponseEntity<List<EarningsReportDTO>> getEarningsReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        var response = this.adminService.getEarningsReport(from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ads-purchased")
    public ResponseEntity<List<PurchasedAdDTO>> getPurchasedAds(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        var response = this.adminService.getPurchasedAdsReport(from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/advertiser-earnings")
    public ResponseEntity<List<AdvertiserEarningsDTO>> getAdvertiserEarnings(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String username
    ) {
        var response = this.adminService.getAdvertiserEarningsReport(from, to, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular-magazines")
    public ResponseEntity<List<MagazineWithSubscriptionsDTO>> getPopularMagazines(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        var response = this.adminService.getFiveMostPopularMagazinesReport(from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/commented-magazines")
    public ResponseEntity<List<MagazineWithCommentsDTO>> getMostCommentedMagazines(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        var response = this.adminService.getFiveMostCommentedMagazinesReport(from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/effectiveness")
    public ResponseEntity<List<EffectivenessReportDTO>> getEffectiveness(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        var response = this.adminService.getEffectivenessReport(from, to);
        return ResponseEntity.ok(response);
    }

}
