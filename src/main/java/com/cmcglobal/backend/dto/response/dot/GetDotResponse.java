package com.cmcglobal.backend.dto.response.dot;

import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class GetDotResponse extends ResponseData {
    private List<DotDTO> dotResponses;
    private Metadata metadata;
}