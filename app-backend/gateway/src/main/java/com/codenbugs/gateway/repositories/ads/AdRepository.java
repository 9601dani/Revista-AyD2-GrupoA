package com.codenbugs.gateway.repositories.ads;

import com.codenbugs.gateway.models.ads.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Integer> {
}
