package com.cmcglobal.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO {
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

    private Boolean isEnable;
}
