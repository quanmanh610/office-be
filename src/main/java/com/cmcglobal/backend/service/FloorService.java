package com.cmcglobal.backend.service;

import com.cmcglobal.backend.dto.request.floor.FloorRequest;
import com.cmcglobal.backend.dto.request.floor.FloorUpdateRequest;
import com.cmcglobal.backend.dto.response.FloorResponsePagingDTO;
import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FloorService {
    ResponseEntity<BaseResponse<String>> createFloor (FloorRequest floorRequest);
    FloorResponse updateFloor(FloorUpdateRequest floorUpdateRequest, int floorId);
    void updateFloorEnable(int floorId);
    void deleteFloor(int floorId);

    FloorResponsePagingDTO getFloors(Integer pageNo, Integer pageSize, String sortBy, String sortType);
    FloorResponsePagingDTO getFloorByName(Integer pageNo, Integer pageSize, String sortBy, String sortType, String floorName);

    List<FloorResponse> getEnabledFloors();
    List<FloorResponse> getEnabledFloorsInBuilding(int buildingId);
}
