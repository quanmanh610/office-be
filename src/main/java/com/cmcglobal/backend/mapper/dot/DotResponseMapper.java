package com.cmcglobal.backend.mapper.dot;

import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.entity.Dot;

import java.util.List;

public interface DotResponseMapper {

    List<DotDTO> toListDotDTO(List<Dot> entityList);

    DotDTO toDotDTO(Dot entity);

    List<ReportDotResponse.DotDTO> toListDotReportDTO(List<Dot> entityList);

    ReportDotResponse.DotDTO toDotReportDTO(Dot entity);
}
