package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.request.floor.FloorUpdateRequest;
import com.cmcglobal.backend.entity.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FloorUpdateRequestMapper extends MapStructMapper<Floor, FloorUpdateRequest>{

    @Override
    @Mapping(target = "building", ignore = true)
    Floor toEntity(FloorUpdateRequest dto);

    @Override
    FloorUpdateRequest toDTO(Floor entity);

    @Override
    List<Floor> toListEntity(List<FloorUpdateRequest> listDto);

    @Override
    List<FloorUpdateRequest> toListDTO(List<Floor> listEntity);
}
