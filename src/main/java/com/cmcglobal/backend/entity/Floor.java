package com.cmcglobal.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "floor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Floor extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "floor_name")
    private String floorName;

    @Column(name = "cost")
    @PositiveOrZero
    private Float cost;

    @Column(name = "price")
    @PositiveOrZero
    private Float price;

    @Column(name = "dot_price_per_month")
    @PositiveOrZero
    private Float dotPricePerMonth;

    @Column(name = "number_of_seat_dot")
    @PositiveOrZero
    private Integer numberOfSeatDot;

    @Column(name = "number_of_room_dot")
    @PositiveOrZero
    private Integer numberOfRoomDot;

    @Column(name = "background_floor")
    private String backgroundFloor;

    @Column(name = "is_enable")
    private Boolean isEnable = true;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "floor", cascade = CascadeType.ALL)
    private List<Dot> dotList;
}