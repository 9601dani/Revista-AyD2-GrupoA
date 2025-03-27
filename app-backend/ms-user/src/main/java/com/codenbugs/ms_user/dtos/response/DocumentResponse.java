package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DocumentResponse(

        Integer id,
        Integer FK_Magazine,
        String path


) {

}

