package com.cmcglobal.backend.mapper.dot;

import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.repository.UserFlattenedRepository;
import com.cmcglobal.backend.service.PoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.cmcglobal.backend.constant.Constant.YYYY_MM_DD;

@Component
public class DotResponseMapperImpl implements DotResponseMapper {

    @Autowired
    private PoaService poaService;

    @Autowired
    private UserFlattenedRepository userFlattenedRepository;

    @Override
    public List<DotDTO> toListDotDTO(List<Dot> entityList) {
        if (entityList == null) {
            return null;
        }

        List<DotDTO> list = new ArrayList<>(entityList.size());
        for (Dot dot : entityList) {
            list.add(toDotDTO(dot));
        }
        return list;
    }

    @Override
    public DotDTO toDotDTO(Dot entity) {
        if (entity == null) {
            return null;
        }

        DotDTO dotDTO = new DotDTO();
        if (entity.getMember() != null) {
            UserFlattened user = userFlattenedRepository.findByUserName(entity.getMember());
            if (user != null) {
                dotDTO.setUsername(user.getUserName());
                dotDTO.setFullName(user.getFullName());
            }
        }
        if (entity.getOwner() != null) {
            UserFlattened owner = userFlattenedRepository.findByUserName(entity.getOwner());
            if (owner != null) {
                dotDTO.setOwner(owner.getFullName());
                dotDTO.setDepartment(owner.getDepartmentName());
                dotDTO.setGroup(owner.getParentDepartmentName() != null ? owner.getParentDepartmentName() : owner.getDepartmentName());
            }
        }
        dotDTO.setFloorName(entity.getFloor().getFloorName());
        dotDTO.setFloorId(entity.getFloor().getId());
        dotDTO.setBuildingName(entity.getFloor().getBuilding().getBuildingName());
        dotDTO.setBuildingId(entity.getFloor().getBuilding().getId());
        dotDTO.setId(entity.getId());
        dotDTO.setCoordinateX(entity.getCoordinateX());
        dotDTO.setCoordinateY(entity.getCoordinateY());
        if (entity.getFromDate() != null) {
            dotDTO.setFromDate(entity.getFromDate().format(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        }
        if (entity.getToDate() != null) {
            dotDTO.setToDate(entity.getToDate().format(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        }
        dotDTO.setPrice(entity.getFloor().getDotPricePerMonth());
        dotDTO.setStatus(entity.getStatus());
        dotDTO.setType(entity.getType());
        dotDTO.setIsActive(entity.getIsActive());

        return dotDTO;
    }

    @Override
    public List<ReportDotResponse.DotDTO> toListDotReportDTO(List<Dot> entityList) {
        if (entityList == null) {
            return null;
        }

        List<ReportDotResponse.DotDTO> list = new ArrayList<>(entityList.size());
        for (Dot dot : entityList) {
            list.add(toDotReportDTO(dot));
        }

        return list;
    }

    @Override
    public ReportDotResponse.DotDTO toDotReportDTO(Dot entity) {
        if (entity == null) {
            return null;
        }

        ReportDotResponse.DotDTO dotDTO = new ReportDotResponse.DotDTO();
        if (entity.getMember() != null) {
            UserFlattened user = poaService.getUserInfoByUsername(entity.getMember());
            if (user != null) {
                dotDTO.setUsername(user.getUserName());
                dotDTO.setFullName(user.getFullName());
            }
        }
        if (entity.getOwner() != null) {
            UserFlattened owner = poaService.getUserInfoByUsername(entity.getOwner());
            if (owner != null) {
                dotDTO.setOwner(owner.getFullName());
                dotDTO.setDepartment(owner.getDepartmentName());
                dotDTO.setGroup(owner.getParentDepartmentName());
            }
        }
        dotDTO.setFloorName(entity.getFloor().getFloorName());
        dotDTO.setBuildingName(entity.getFloor().getBuilding().getBuildingName());
        dotDTO.setFromDate(entity.getFromDate());
        dotDTO.setToDate(entity.getToDate());
        dotDTO.setPrice(entity.getFloor().getDotPricePerMonth());
        dotDTO.setStatus(entity.getStatus());
        dotDTO.setType(entity.getType());
        dotDTO.setIsActive(entity.getIsActive());

        return dotDTO;
    }
}
