package com.cmcglobal.backend.dto.response.ticket;

import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.ResponseData;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketResponse extends ResponseData {
    private List<TicketDTO> ticketResponses;
    private Metadata metadata;
}