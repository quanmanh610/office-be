package com.cmcglobal.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Table(name = "dot")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dot extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "coordinate_x")
    private Float coordinateX;

    @Column(name = "coordinate_y")
    private Float coordinateY;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "member")
    private String member;

    @Column(name = "owner")
    private String owner;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dots", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Ticket> tickets;

}