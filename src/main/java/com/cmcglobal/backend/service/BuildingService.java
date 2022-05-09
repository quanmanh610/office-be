package com.cmcglobal.backend.service;

import com.cmcglobal.backend.dto.request.building.BuildingRequest;
import com.cmcglobal.backend.dto.response.BuildingResponsePagingDTO;
import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BuildingService {
    ResponseEntity<BaseResponse<BuildingResponse>> createBuilding(BuildingRequest buildingRequest);
    ResponseEntity<BaseResponse<BuildingResponse>> updateBuilding(BuildingRequest buildingRequest, int buildingId);
    ResponseEntity<BaseResponse<BuildingResponse>> deleteBuilding(int buildingId);
    void deleteMultipleBuildings(List<Integer> buildingIds);
    BuildingResponsePagingDTO getAllBuildings(Integer pageNo, Integer pageSize, String sortBy, String sortType);
    BuildingResponsePagingDTO searchBuilding(String textSearch,Integer pageNo, Integer pageSize, String sortBy, String sortType);
    List<BuildingResponse> findAllEnabledBuildings();
}
