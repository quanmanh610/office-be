package com.cmcglobal.backend.dto.response.ticket;

import com.cmcglobal.backend.dto.response.dot.DotDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetail {

    private List<DotDTO> dotDTOList;

    private String FloorBackground;
}
