package com.cmcglobal.backend.mapper.ticket;

import com.cmcglobal.backend.dto.response.ticket.TicketDTO;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.entity.immutable.TicketDotView;
import com.cmcglobal.backend.repository.UserFlattenedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.cmcglobal.backend.constant.Constant.YYYY_MM_DD;

@Component
public class TicketDotResponseMapperImpl implements TicketDotResponseMapper {

    @Autowired
    private UserFlattenedRepository userFlattenedRepository;

    @Override
    public TicketDTO toDTO(TicketDotView entity) {
        if (entity == null) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();
        UserFlattened owner = userFlattenedRepository.findByUserName(entity.getOwner());

        ticketDTO.setUsername(owner.getUserName());
        ticketDTO.setOwner(owner.getFullName());
        ticketDTO.setDepartment(owner.getDepartmentName());
        ticketDTO.setGroup(owner.getParentDepartmentName() != null ? owner.getParentDepartmentName() : owner.getDepartmentName());
        ticketDTO.setBuildingName(entity.getFloor().getBuilding().getBuildingName());
        ticketDTO.setFloorName(entity.getFloor().getFloorName());
        ticketDTO.setStatus(entity.getState());
        ticketDTO.setId(entity.getId());
        ticketDTO.setType(entity.getType());
        if (entity.getFromDate() != null) {
            ticketDTO.setFromDate(entity.getFromDate().format(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        }
        if (entity.getToDate() != null) {
            ticketDTO.setToDate(entity.getToDate().format(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        }
        ticketDTO.setQuantity(entity.getQuantity());

        return ticketDTO;
    }

    @Override
    public List<TicketDTO> toListDTO(List<TicketDotView> entityList) {
        if (entityList == null) {
            return null;
        }

        List<TicketDTO> list = new ArrayList<>(entityList.size());
        for (TicketDotView ticketDotView : entityList) {
            list.add(toDTO(ticketDotView));
        }

        return list;
    }
}
