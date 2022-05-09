package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.request.floor.FloorRequest;
import com.cmcglobal.backend.entity.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorRequestMapper extends MapStructMapper<Floor, FloorRequest> {

    @Override
    @Mapping(target = "building", ignore = true)
    Floor toEntity(FloorRequest dto);

    @Override
    FloorRequest toDTO(Floor entity);

    @Override
    List<Floor> toListEntity(List<FloorRequest> listDto);

    @Override
    List<FloorRequest> toListDTO(List<Floor> listEntity);
}
