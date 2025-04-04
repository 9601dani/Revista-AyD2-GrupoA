package com.codenbugs.ms_user.repositories.admin;

import com.codenbugs.ms_user.dtos.response.admin.*;
import com.codenbugs.ms_user.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer> {

    @Query(value = """
        SELECT
            m.id AS magazineId,
            m.name AS magazineName,
            IFNULL(SUM(DISTINCT p.cost), 0) AS totalAdCost,
            IFNULL(SUM(s.pay), 0) AS totalIncome,
            IFNULL(SUM(DISTINCT p.cost), 0) + (0.6 * IFNULL(SUM(s.pay), 0)) AS totalProfit
        FROM magazines m
        LEFT JOIN ads a ON a.FK_User = m.FK_User
          AND a.date_created BETWEEN :from AND :to
        LEFT JOIN periods p ON p.id = a.FK_Period
        LEFT JOIN suscription s ON s.FK_Magazine = m.id
          AND s.date_created BETWEEN :from AND :to
        GROUP BY m.id, m.name
        ORDER BY totalProfit DESC;
    """, nativeQuery = true)
    List<EarningsReportDTO> getEarningsReport(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(value = """
        SELECT
            a.id AS adId,
            u.username AS user,
            at.name AS adType,
            p.name AS period,
            p.cost AS cost,
            a.date_created AS dateCreated,
            a.date_ended AS dateEnded,
            a.number_views AS numberViews,
            a.is_enabled AS isEnabled
        FROM ads a
        JOIN ad_types at ON at.id = a.FK_Ad_Types
        JOIN periods p ON p.id = a.FK_Period
        JOIN users u ON u.id = a.FK_User
        WHERE (:from IS NULL OR :to IS NULL OR a.date_created BETWEEN :from AND :to)
          AND (at.name = 'IMAGE' OR 'IMAGE' IS NULL)
          AND (p.name = 'DAILY' OR 'DAILY' IS NULL)
        ORDER BY a.date_created DESC;
    """, nativeQuery = true)
    List<PurchasedAdDTO> getPurchasedAdsReport(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(value = """
        SELECT
            u.id AS userId,
            u.username AS username,
            a.id AS adId,
            a.date_created AS dateCreated,
            at.name AS adType,
            p.name AS period,
            p.cost AS cost,
            a.number_views AS numberViews,
            a.is_enabled AS isEnabled
        FROM ads a
            JOIN ad_types at ON a.FK_Ad_Types = at.id
            JOIN periods p ON a.FK_Period = p.id
            JOIN users u ON a.FK_User = u.id
        WHERE (:from IS NULL OR :to IS NULL OR a.date_created BETWEEN :from AND :to)
          AND (:userId IS NULL OR u.id = :userId)
        ORDER BY u.id, a.date_created DESC;
    """, nativeQuery = true)
    List<AdvertiserEarningsDTO> getAdvertiserEarningsReport(@Param("from") LocalDate from, @Param("to") LocalDate to, @Param("userId") Integer userId);

    @Query(value = """
        SELECT
            m.id AS magazineId,
            m.name AS magazineName,
            u.username AS subscriber,
            s.date_created AS dateCreated,
            s.pay AS pay,
            s.is_like AS isLike
        FROM suscription s
        JOIN magazines m ON m.id = s.FK_Magazine
        JOIN users u ON u.id = s.FK_User
        JOIN (
            SELECT FK_Magazine
            FROM suscription
            WHERE (:from IS NULL OR :to IS NULL OR date_created BETWEEN :from AND :to)
            GROUP BY FK_Magazine
            ORDER BY COUNT(*) DESC
            LIMIT 5
        ) top5 ON top5.FK_Magazine = s.FK_Magazine
        WHERE (:from IS NULL OR :to IS NULL OR s.date_created BETWEEN :from AND :to)
        ORDER BY m.id, s.date_created DESC;
    """, nativeQuery = true)
    List<FlatPopularMagazineDTO> getFiveMostPopularMagazinesReport(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(value = """
        SELECT
            m.id AS magazineId,
            m.name AS magazineName,
            u.username AS commenter,
            c.content AS comment,
            DATE(c.date_created) AS dateCreated
        FROM comments c
        JOIN magazines m ON m.id = c.FK_Magazine
        JOIN suscription s ON s.id = c.FK_Suscription
        JOIN users u ON u.id = s.FK_User
        JOIN (
            SELECT FK_Magazine
            FROM comments
            WHERE (:from IS NULL OR :to IS NULL OR date_created BETWEEN :from AND :to)
            GROUP BY FK_Magazine
            ORDER BY COUNT(*) DESC
            LIMIT 5
        ) top5 ON top5.FK_Magazine = c.FK_Magazine
        WHERE (:from IS NULL OR :to IS NULL OR c.date_created BETWEEN :from AND :to)
        ORDER BY m.id, c.date_created DESC;
    """, nativeQuery = true)
    List<FlatMostCommentedMagazineDTO> getFiveMostCommentedMagazinesReport(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(value = """
        SELECT
            u.id AS userId,
            u.username AS username,
            a.id AS adId,
            a.date_created AS dateCreated,
            a.date_ended AS dateEnded,
            a.number_views AS numberViews,
            at.name AS adType,
            p.name AS periodName,
            p.cost AS adCost
        FROM ads a
        JOIN users u ON u.id = a.FK_User
        JOIN ad_types at ON at.id = a.FK_Ad_Types
        JOIN periods p ON p.id = a.FK_Period
        WHERE (:from IS NULL OR :to IS NULL OR a.date_created BETWEEN :from AND :to)
        ORDER BY u.id, a.date_created DESC;
    """, nativeQuery = true)
    List<EffectivenessReportDTO> getEffectivenessReport(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
