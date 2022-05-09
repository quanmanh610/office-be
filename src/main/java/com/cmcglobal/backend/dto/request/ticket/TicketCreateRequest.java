package com.cmcglobal.backend.dto.request.ticket;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketCreateRequest {
    private List<Integer> dotIds;
    private String type;
    private String fromDate;
    private String toDate;
    private String owner;
}
