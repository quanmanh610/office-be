package com.cmcglobal.backend.dto.response;

import com.cmcglobal.backend.dto.BuildingDTO;
import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import lombok.Data;

import java.util.List;

@Data
public class BuildingResponsePagingDTO extends ResponseData {
    final List<BuildingResponse> responses;
    final Metadata metadata;
}
