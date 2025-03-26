package com.codenbugs.ms_user.dtos.user_information;

import java.math.BigDecimal;

public record UserInformationCurrentRequest(
        Integer fkUser,
        Boolean sum,
        BigDecimal current_balance
) {
}
