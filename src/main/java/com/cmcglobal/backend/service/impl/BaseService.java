package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.exception.TicketException;
import com.cmcglobal.backend.repository.FloorRepository;
import com.cmcglobal.backend.service.PoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.cmcglobal.backend.constant.Constant.YYYY_MM_DD;

@Slf4j
public class BaseService {
    @Autowired
    FloorRepository floorRepository;

    @Autowired
    PoaService poaService;

    public List<String> getUserIdInGroup(String department) {
        List<String> userIdInGroup;
        if (!"".equals(department)) {
            userIdInGroup = poaService.getUsernameListByGroupName(department);
            if (userIdInGroup.isEmpty()) {
                throw new TicketException(ErrorMessage.USER_NOT_FOUND_IN_GROUP);
            }
            return userIdInGroup;
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> getManagersUsername() {
        List<String> managersUsername;
        managersUsername = poaService.getManagersUsername();
        if (managersUsername.isEmpty()) {
            throw new TicketException(ErrorMessage.USER_NOT_FOUND_IN_GROUP);
        }
        return managersUsername;
    }

    public List<Integer> getFloorIdByBuilding(List<Integer> floorIds, Integer buildingId) {
        if (buildingId != 0 && floorIds.isEmpty()) {
            floorIds = floorRepository.findFloorIdByBuildingId(buildingId);
            if (floorIds.isEmpty()) {
                throw new TicketException(ErrorMessage.FLOOR_NOT_FOUND);
            }
        }
        return floorIds;
    }


    public String getParseDate(String date) {
        if (!"".equals(date)) {
            try {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(YYYY_MM_DD);
                return LocalDate.parse(date, dateFormat).toString();
            } catch (Exception e) {
                log.error(e.toString());
                throw new TicketException(ErrorMessage.DATE_FORMAT);
            }
        } else {
            return null;
        }
    }

    public String getUsername(String username) {
        if (!"".equals(username)) {
            UserFlattened user = poaService.getUserInfoByUsername(username);
            if (user == null) {
                throw new TicketException(ErrorMessage.USER_NOT_FOUND);
            }
            return user.getUserName();
        } else {
            return null;
        }
    }
}
