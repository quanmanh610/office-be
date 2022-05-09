package com.cmcglobal.backend.dto.request.floor;

import com.cmcglobal.backend.dto.request.dot.CreateDotRequest;
import com.cmcglobal.backend.dto.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorRequest extends ResponseData {
    @NotNull
    private String backgroundFloor;

    @NotNull
    @NotBlank()
    private Integer buildingId;

    @PositiveOrZero
    private Float cost;

    @PositiveOrZero
    private Float dotPricePerMonth;

    @PositiveOrZero
    private Integer numberOfSeatDot;

    @PositiveOrZero
    private Integer numberOfRoomDot;

    private List<CreateDotRequest> dots;

    @NotNull
    @NotBlank()
    private String floorName;
}

