package com.codenbugs.ms_user.dtos.response.admin;

import java.util.List;

public record MagazineWithSubscriptionsDTO (
        Integer magazineId,
        String magazineName,
        List<SubscriptionDetailDTO> subscriptions
) {}
