package com.cmcglobal.backend.mapper;

import com.cmcglobal.backend.dto.FloorDTO;
import com.cmcglobal.backend.entity.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorMapper extends MapStructMapper<Floor, FloorDTO> {

    @Override
    @Mapping(target = "building", ignore = true)
    Floor toEntity(FloorDTO dto);

    @Override
    FloorDTO toDTO(Floor entity);

    @Override
    List<Floor> toListEntity(List<FloorDTO> listDto);

    @Override
    List<FloorDTO> toListDTO(List<Floor> listEntity);
}
