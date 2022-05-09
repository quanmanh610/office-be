package com.cmcglobal.backend.dto.request.floor;

import com.cmcglobal.backend.dto.response.ResponseData;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class FloorUpdateRequest extends ResponseData implements Serializable {
    private int id;

    @NotNull
    private String backgroundFloor;

    @NotNull
    @NotBlank()
    private Integer buildingId;

    @NotNull
    @NotBlank
    @PositiveOrZero
    private Float cost;

    @NotNull
    @NotBlank
    @PositiveOrZero
    private Float dotPricePerMonth;

    private List<DotDTO> dots;

    private List<Integer> deleteDots;

    @NotNull
    @NotBlank()
    private String floorName;
}