package com.codenbugs.gateway.repositories.ads;

import com.codenbugs.gateway.models.ads.AdType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdTypeRepository extends JpaRepository<AdType, Long> {
}
