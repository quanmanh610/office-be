package com.cmcglobal.backend.dto.response.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private String owner;
    private String username;
    private String floorName;
    private String buildingName;
    private String department;
    private String group;
    private String status;
    private String type;
    private String fromDate;
    private String toDate;
    private Integer quantity;
}
