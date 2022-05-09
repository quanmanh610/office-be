package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import com.cmcglobal.backend.entity.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorResponseMapper extends MapStructMapper<Floor, FloorResponse> {

    @Override
    @Mapping(target = "building", ignore = true)
    Floor toEntity(FloorResponse dto);

    @Override
    FloorResponse toDTO(Floor entity);

    @Override
    List<Floor> toListEntity(List<FloorResponse> listDto);

    @Override
    List<FloorResponse> toListDTO(List<Floor> listEntity);
}
