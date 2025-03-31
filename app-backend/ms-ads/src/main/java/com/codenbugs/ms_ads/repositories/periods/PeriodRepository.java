package com.codenbugs.ms_ads.repositories.periods;

import com.codenbugs.ms_ads.models.periods.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
