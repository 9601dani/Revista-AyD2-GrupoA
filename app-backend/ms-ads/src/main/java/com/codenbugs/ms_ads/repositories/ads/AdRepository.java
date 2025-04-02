package com.codenbugs.ms_ads.repositories.ads;

import com.codenbugs.ms_ads.models.ads.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Integer> {
}
