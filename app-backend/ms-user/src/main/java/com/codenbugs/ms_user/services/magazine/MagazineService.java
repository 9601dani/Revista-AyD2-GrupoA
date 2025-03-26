package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;

import java.util.List;

public interface MagazineService {

    MagazineResponse saveMagazine(MagazineRequest magazine, String path) throws UserNotFoundException;
    List<MagazineResponse> findAll();
}
