package com.cmcglobal.backend.entity.immutable;

import com.cmcglobal.backend.entity.Floor;
import lombok.Data;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket_dot_view")
@Immutable
public class TicketDotView {
    @Id
    private Integer id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private String type;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "floor_id")
    private Floor floor;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
