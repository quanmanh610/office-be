package com.cmcglobal.backend.mapper.ticket;

import com.cmcglobal.backend.dto.response.ticket.TicketDetail;
import com.cmcglobal.backend.entity.Ticket;
import com.cmcglobal.backend.mapper.MapStructMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketDetailMapper extends MapStructMapper<Ticket, TicketDetail> {

    @Override
    Ticket toEntity(TicketDetail dto);

    @Override
    TicketDetail toDTO(Ticket entity);
}
