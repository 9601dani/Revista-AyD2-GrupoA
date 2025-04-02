package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineWithDocumentsResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MagazineService {

    MagazineResponse saveMagazine(MagazineRequest magazine) throws UserNotFoundException;
    List<MagazineWithDocumentsResponse> getByUserId(Integer userId) throws UserNotFoundException;
    List<AllMagazineResponse> getAllMagazines();
    AllMagazineResponse getMagazineById(Integer magazineId);
    AllMagazineResponse updateMagazine(MagazineRequest magazine);
}
