package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.request.building.BuildingRequest;
import com.cmcglobal.backend.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuildingRequestMapper extends MapStructMapper<Building, BuildingRequest>{

    @Override
    @Mapping(target = "floor", ignore = true)
    Building toEntity(BuildingRequest dto);

    @Override
    BuildingRequest toDTO(Building entity);

    @Override
    List<Building> toListEntity(List<BuildingRequest> listDTO);

    @Override
    List<BuildingRequest> toListDTO(List<Building> listEntity);
}
