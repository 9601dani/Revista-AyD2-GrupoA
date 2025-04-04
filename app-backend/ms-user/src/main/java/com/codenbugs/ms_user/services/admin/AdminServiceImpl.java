package com.codenbugs.ms_user.services.admin;

import com.codenbugs.ms_user.dtos.response.admin.*;
import com.codenbugs.ms_user.repositories.admin.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<EarningsReportDTO> getEarningsReport(LocalDate from, LocalDate to) {
        return this.adminRepository.getEarningsReport(from, to);
    }

    @Override
    public List<PurchasedAdDTO> getPurchasedAdsReport(LocalDate from, LocalDate to) {
        return this.adminRepository.getPurchasedAdsReport(from, to);
    }

    @Override
    public List<AdvertiserEarningsDTO> getAdvertiserEarningsReport(LocalDate from, LocalDate to, String username) {
        String usernameFilter = username == null ? "" : username;
        return this.adminRepository.getAdvertiserEarningsReport(from, to, usernameFilter);
    }

    @Override
    public List<MagazineWithSubscriptionsDTO> getFiveMostPopularMagazinesReport(LocalDate from, LocalDate to) {
        List<FlatPopularMagazineDTO> popularMagazineDTOS = this.adminRepository.getFiveMostPopularMagazinesReport(from, to);
        List<MagazineWithSubscriptionsDTO> magazineWithSubscriptionsDTOS = new ArrayList<>();
        for(FlatPopularMagazineDTO flatPopularMagazineDTO : popularMagazineDTOS) {
            List<SubscriptionDetailDTO> detailDTOS = popularMagazineDTOS.stream()
                    .filter(m -> m.magazineId().equals(flatPopularMagazineDTO.magazineId()))
                    .map(m -> new SubscriptionDetailDTO(m.subscriber(), m.dateCreated(), m.pay(), m.isLike()))
                    .toList();

            MagazineWithSubscriptionsDTO magazine = new MagazineWithSubscriptionsDTO(
                    flatPopularMagazineDTO.magazineId(),
                    flatPopularMagazineDTO.magazineName(),
                    detailDTOS
            );

            magazineWithSubscriptionsDTOS.add(magazine);

        }
        return magazineWithSubscriptionsDTOS;
    }

    @Override
    public List<MagazineWithCommentsDTO> getFiveMostCommentedMagazinesReport(LocalDate from, LocalDate to) {
        List<FlatMostCommentedMagazineDTO> flatMostCommentedMagazineDTOS = this.adminRepository.getFiveMostCommentedMagazinesReport(from, to);
        List<MagazineWithCommentsDTO> magazineWithCommentsDTOS = new ArrayList<>();
        for(FlatMostCommentedMagazineDTO flat: flatMostCommentedMagazineDTOS) {
            List<CommentDetailDTO> comments = flatMostCommentedMagazineDTOS.stream()
                    .filter(m -> m.magazineId().equals(flat.magazineId()))
                    .map(m -> new CommentDetailDTO(m.commenter(), m.comment(), m.dateCreated()))
                    .toList();

            MagazineWithCommentsDTO magazineWithCommentsDTO = new MagazineWithCommentsDTO(
                    flat.magazineId(),
                    flat.magazineName(),
                    comments
            );

            magazineWithCommentsDTOS.add(magazineWithCommentsDTO);
        }
        return magazineWithCommentsDTOS;
    }

    @Override
    public List<EffectivenessReportDTO> getEffectivenessReport(LocalDate from, LocalDate to) {
        return this.adminRepository.getEffectivenessReport(from, to);
    }
}
