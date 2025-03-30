package com.codenbugs.gateway.repositories.ads;

import com.codenbugs.gateway.models.ads.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
