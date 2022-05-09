package com.cmcglobal.backend.dto.response;

import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import lombok.Data;

import java.util.List;

@Data
public class FloorResponsePagingDTO extends ResponseData {
    final List<FloorResponse> response;
    final Metadata metadata;
}
