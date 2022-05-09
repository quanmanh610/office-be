package com.cmcglobal.backend.dto.request.building;

import com.cmcglobal.backend.dto.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingRequest extends ResponseData {
    @NotNull
    @NotBlank
    private String buildingName;

    @NotNull
    @NotBlank
    private String buildingAddress;

    private Boolean isEnable;
}
