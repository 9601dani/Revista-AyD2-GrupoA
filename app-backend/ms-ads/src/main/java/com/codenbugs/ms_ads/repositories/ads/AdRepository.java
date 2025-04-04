package com.codenbugs.ms_ads.repositories.ads;

import com.codenbugs.ms_ads.models.ads.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Integer> {

    @Query(value ="""
            SELECT a.* FROM ads a
                  JOIN ad_has_labels ahl ON a.id = ahl.FK_Ad
                  JOIN user_has_labels uhl ON ahl.FK_Label = uhl.FK_Label
                  WHERE uhl.FK_User = :userId
                    AND a.is_enabled = 1
                    AND a.date_ended >= CURDATE()
                  ORDER BY RAND()
                  LIMIT 1
              """, nativeQuery = true)
    Optional<Ad> findRandomByUserId(@Param("userId") Integer userId);


    @Query(value ="""
            SELECT a.* FROM ads a
                  WHERE a.is_enabled = 1
                    AND a.date_ended >= CURDATE()
                  ORDER BY RAND()
                  LIMIT 1
              """, nativeQuery = true)
    Optional<Ad> findRandom();

    List<Ad> findByUserId(Integer userId);
}
