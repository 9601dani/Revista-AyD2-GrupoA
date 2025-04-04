package com.codenbugs.ms_user.dtos.suscription;

import java.math.BigDecimal;

public record SuscriptionRequestDto (
    Integer fkUser,
    Integer fkMagazine,
    BigDecimal pay
) {}
