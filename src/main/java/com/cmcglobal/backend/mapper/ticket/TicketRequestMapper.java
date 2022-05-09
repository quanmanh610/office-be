package com.cmcglobal.backend.mapper.ticket;

import com.cmcglobal.backend.dto.request.ticket.TicketCreateRequest;
import com.cmcglobal.backend.entity.Ticket;
import com.cmcglobal.backend.mapper.CommonMapper;
import com.cmcglobal.backend.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CommonMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketRequestMapper extends MapStructMapper<Ticket, TicketCreateRequest> {

    @Override
    @Mapping(source = "owner", target = "owner", qualifiedByName = "GET_USER_ID_BY_USERNAME")
    Ticket toEntity(TicketCreateRequest dto);
}
