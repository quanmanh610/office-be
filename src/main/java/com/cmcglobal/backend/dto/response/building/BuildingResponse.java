package com.cmcglobal.backend.dto.response.building;

import com.cmcglobal.backend.dto.response.ResponseData;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponse extends ResponseData {
    private int id;

    @NotNull
    @NotBlank
    private String buildingName;

    @NotNull
    @NotBlank
    private String buildingAddress;

    @PositiveOrZero
    private Integer numberOfFloor;

    @PositiveOrZero
    private Integer numberOfSeatDot;

    @PositiveOrZero
    private Integer numberOfRoomDot;

    @PositiveOrZero
    private Float cost;

    @PositiveOrZero
    private Float price;

    private Boolean isEnable;
}
