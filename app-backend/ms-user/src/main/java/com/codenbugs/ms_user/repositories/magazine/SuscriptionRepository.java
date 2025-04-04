package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.dtos.report.PaymentReportDto;
import com.codenbugs.ms_user.dtos.report.SuscriptionReportDto;
import com.codenbugs.ms_user.dtos.report.TopLikedMagazineDto;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.magazine.Suscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SuscriptionRepository extends JpaRepository<Suscription, Integer> {
    List<Suscription> findByUserId(Integer userId);

    @Query("""
        SELECT new com.codenbugs.ms_user.dtos.report.SuscriptionReportDto(
            s.id, s.dateCreated, s.dateEnded, s.isLike, s.pay, m.id,
            m.name, m.description, m.price
        )
        FROM Suscription s
        JOIN s.magazine m
        JOIN m.user u
          WHERE s.dateCreated BETWEEN :start AND :end
          AND (:magazineId IS NULL OR m.id = :magazineId)
        ORDER BY s.dateCreated DESC
    """)
    List<SuscriptionReportDto> findSuscriptionsByFilters(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("authorId") Integer authorId,
            @Param("magazineId") Integer magazineId
    );

    @Query("""
        SELECT new com.codenbugs.ms_user.dtos.report.TopLikedMagazineDto(
            m.id,
            m.name,
            m.description,
            m.price,
            COUNT(s.id)
        )
        FROM Suscription s
        JOIN s.magazine m
        WHERE s.isLike = true
          AND s.dateCreated BETWEEN :startDate AND :endDate
        GROUP BY m.id, m.name, m.description, m.price, m.type
        ORDER BY COUNT(s.id) DESC
    """)
    List<TopLikedMagazineDto> findTopLikedMagazinesInRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT new com.codenbugs.ms_user.dtos.report.PaymentReportDto(
            m.id,
            m.name,
            s.dateCreated,
            s.pay
        )
        FROM Suscription s
        JOIN s.magazine m
        WHERE s.dateCreated BETWEEN :startDate AND :endDate
          AND (:magazineId IS NULL OR m.id = :magazineId)
        ORDER BY m.name, s.dateCreated
    """)
    List<PaymentReportDto> findPaymentReport(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("magazineId") Integer magazineId
    );
}
