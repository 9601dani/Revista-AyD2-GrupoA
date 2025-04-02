package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.suscription.AllSuscriptionResponseDto;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionRequestDto;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Suscription;

import java.util.List;

public interface SuscriptionService {

    SuscriptionResponseDto saveSuscription(SuscriptionRequestDto suscriptionRequestDto) throws UserNotFoundException;

    List<SuscriptionResponseDto> getSuscriptionsByUserId(Integer userId);

    List<AllSuscriptionResponseDto> getSuscriptionsWithMagazineByUserId(Integer userId);

    AllSuscriptionResponseDto getSuscriptionById(Integer id) throws UserNotFoundException;
}
