package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Modifying
    @Query(value = "UPDATE ticket SET state = :action WHERE id IN (:ticketIds)", nativeQuery = true)
    void updateTickets(@Param("ticketIds") List<Integer> ticketIds, @Param("action") String action);

    @Query(value = "SELECT DISTINCT dot_id FROM ticket_dot WHERE ticket_id IN (:ticketIds)", nativeQuery = true)
    List<Integer> findDotIdsByTicketId(@Param("ticketIds") Integer ticketIds);


    @Query(value = "SELECT DISTINCT dot_id FROM ticket_dot WHERE ticket_id IN (:ticketIds)", nativeQuery = true)
    List<Integer> findDotIdsByTicketId(@Param("ticketIds") List<Integer> ticketIds);

    @Query(value = "SELECT * FROM ticket WHERE id = :ticketId", nativeQuery = true)
    Ticket getTicketByID(Integer ticketId);
}
