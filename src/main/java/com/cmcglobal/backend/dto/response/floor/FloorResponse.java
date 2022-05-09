package com.cmcglobal.backend.dto.response.floor;

import com.cmcglobal.backend.dto.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FloorResponse extends ResponseData {
    private int id;

    @NotNull
    @NotBlank()
    private String address;

    @NotNull
    private String backgroundFloor;

    @NotNull
    @NotBlank()
    private String buildingName;

    @NotNull
    @NotBlank()
    private Integer buildingId;

    @NotNull
    @NotBlank()
    private String floorName;

    @PositiveOrZero
    private Float cost;

    @PositiveOrZero
    private Float price;

    @PositiveOrZero
    private Float dotPricePerMonth;

    @PositiveOrZero
    private Integer numberOfRoomDot;

    @PositiveOrZero
    private Integer numberOfSeatDot;

    @NotNull
    private Boolean isEnable;
}
