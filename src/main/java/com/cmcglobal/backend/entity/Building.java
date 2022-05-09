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
@Table(name = "building")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Building extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "building_address")
    private String buildingAddress;

    @Column(name = "number_of_floor")
    private Integer numberOfFloor;

    @Column(name = "number_of_room_dot")
    @PositiveOrZero
    private Integer numberOfRoomDot;

    @Column(name = "number_of_seat_dot")
    @PositiveOrZero
    private Integer numberOfSeatDot;

    @Column(name = "cost")
    @PositiveOrZero
    private Float cost;

    @Column(name = "price")
    @PositiveOrZero
    private Float price;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Floor> floor;
}