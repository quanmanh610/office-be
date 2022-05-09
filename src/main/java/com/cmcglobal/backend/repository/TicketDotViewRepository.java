package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.immutable.TicketDotView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TicketDotViewRepository extends JpaRepository<TicketDotView, Integer> {
    @Query(value = "SELECT * FROM ticket_dot_view" +
            " WHERE (:#{#floorIds.size()} < 1 OR floor_id IN (:floorIds))" +
            " AND (:#{#status.size()} < 1 OR state IN (:status))" +
            " AND (:#{#usernamesInGroup.size()} < 1 OR owner IN (:usernamesInGroup))" +
            " AND (:#{#managersUsername.size()} < 1 OR owner IN (:managersUsername))" +
            " AND (:owner IS NULL OR owner = :owner)" +
            " OR (owner = :adminUsername AND (:#{#status.size()} < 1 OR state IN (:status)))" +
            " AND (:date IS NULL OR (from_date <= :date AND to_date >= :date))" +
            " GROUP BY id, floor_id, owner" +
            " ORDER BY created_at DESC", nativeQuery = true)
    Page<TicketDotView> findTicketsByConditions(@Param("floorIds") List<Integer> floorIds,
                                                @Param("usernamesInGroup") List<String> usernamesInGroup,
                                                @Param("managersUsername") List<String> managersUsername,
                                                @Param("owner") String owner,
                                                @Param("adminUsername") String adminUsername,
                                                @Param("date") String date,
                                                @Param("status") List<String> status,
                                                Pageable paging);
}
