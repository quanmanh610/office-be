package com.cmcglobal.backend.mapper.ticket;

import com.cmcglobal.backend.dto.response.ticket.TicketDTO;
import com.cmcglobal.backend.entity.immutable.TicketDotView;

import java.util.List;

public interface TicketDotResponseMapper {

//    @Mapping(source = "ownerId", target = "username", qualifiedByName = "GET_USERNAME_BY_USER_ID")
//    @Mapping(source = "ownerId", target = "owner", qualifiedByName = "GET_FULL_NAME_BY_USER_ID")
//    @Mapping(source = "ownerId", target = "department", qualifiedByName = "GET_DEPARTMENT")
//    @Mapping(source = "ownerId", target = "group", qualifiedByName = "GET_GROUP")
//    @Mapping(source = "floor", target = "floorName", qualifiedByName = "GET_FLOOR_NAME")
//    @Mapping(source = "floor", target = "buildingName", qualifiedByName = "GET_BUILDING_NAME")
//    @Mapping(source = "state", target = "status")
    TicketDTO toDTO(TicketDotView entity);

    List<TicketDTO> toListDTO(List<TicketDotView> entityList);
}
