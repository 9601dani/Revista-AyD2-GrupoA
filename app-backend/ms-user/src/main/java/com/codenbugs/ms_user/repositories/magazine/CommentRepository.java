package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.dtos.report.CommentReportDto;
import com.codenbugs.ms_user.models.magazine.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("""
        SELECT new com.codenbugs.ms_user.dtos.report.CommentReportDto(
            c.id, c.content, c.dateCreated,
            m.id, m.name, m.description
        )
        FROM Comment c
        JOIN c.magazine m
        WHERE c.dateCreated BETWEEN :start AND :end
        ORDER BY c.dateCreated DESC
    """)
    List<CommentReportDto> findCommentsInDateRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
                SELECT new com.codenbugs.ms_user.dtos.report.CommentReportDto(
                    c.id, c.content, c.dateCreated,
                    m.id, m.name, m.description
                )
                FROM Comment c
                JOIN c.magazine m
                WHERE c.dateCreated BETWEEN :start AND :end
                  AND m.id = :magazineId
                ORDER BY c.dateCreated DESC
            """)
    List<CommentReportDto> findCommentsInDateRangeByMagazine(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("magazineId") Integer magazineId
    );
}
