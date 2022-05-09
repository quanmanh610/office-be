package com.cmcglobal.backend.dto.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTicketRequest {
    private List<TicketDTO> tickets;
    private String action;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TicketDTO {
        private Integer id;
        private String type;
    }
}
