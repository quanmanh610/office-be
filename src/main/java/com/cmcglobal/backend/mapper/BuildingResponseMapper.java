package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import com.cmcglobal.backend.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuildingResponseMapper extends MapStructMapper<Building, BuildingResponse>{

    @Override
    @Mapping(target = "floor", ignore = true)
    Building toEntity(BuildingResponse dto);

    @Override
    BuildingResponse toDTO(Building entity);

    @Override
    List<Building> toListEntity(List<BuildingResponse> listDTO);

    @Override
    List<BuildingResponse> toListDTO(List<Building> listEntity);
}