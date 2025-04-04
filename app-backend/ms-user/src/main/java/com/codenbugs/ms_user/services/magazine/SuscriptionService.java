package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.report.*;
import com.codenbugs.ms_user.dtos.suscription.*;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Suscription;

import java.time.LocalDateTime;
import java.util.List;

public interface SuscriptionService {

    SuscriptionResponseDto saveSuscription(SuscriptionRequestDto suscriptionRequestDto) throws UserNotFoundException;

    List<SuscriptionResponseDto> getSuscriptionsByUserId(Integer userId);

    List<AllSuscriptionResponseDto> getSuscriptionsWithMagazineByUserId(Integer userId);

    AllSuscriptionResponseDto getSuscriptionById(Integer id) throws UserNotFoundException;

    SuscriptionResponseDto updateIsLike(SuscriptionLikeRequest request) throws UserNotFoundException;

    CommentMagazineResponse saveComment(CommentRequest request) throws UserNotFoundException;

    List<CommentReportDto> getCommentReport(CommentReportRequestDto request);

    List<SuscriptionReportDto> getReport(SuscriptionReportRequestDto request);

    List<TopLikedMagazineDto> getTopLikedMagazines(TopLikedRequestDto request);

    List<PaymentReportDto> getPaymentReport(PaymentReportRequestDto request);
}
