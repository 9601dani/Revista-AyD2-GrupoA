package com.codenbugs.ms_user.services.admin;

import com.codenbugs.ms_user.dtos.response.admin.*;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    List<EarningsReportDTO> getEarningsReport(LocalDate from, LocalDate to);
    List<PurchasedAdDTO> getPurchasedAdsReport(LocalDate from, LocalDate to);
    List<AdvertiserEarningsDTO> getAdvertiserEarningsReport(LocalDate from, LocalDate to, Integer userId);
    List<MagazineWithSubscriptionsDTO> getFiveMostPopularMagazinesReport(LocalDate from, LocalDate to);
    List<MagazineWithCommentsDTO> getFiveMostCommentedMagazinesReport(LocalDate from, LocalDate to);
    List<EffectivenessReportDTO> getEffectivenessReport(LocalDate from, LocalDate to);
}
