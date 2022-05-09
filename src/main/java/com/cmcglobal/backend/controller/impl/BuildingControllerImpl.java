package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.controller.BuildingController;
import com.cmcglobal.backend.dto.request.building.BuildingRequest;
import com.cmcglobal.backend.dto.response.BuildingResponsePagingDTO;
import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import com.cmcglobal.backend.service.BuildingService;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingControllerImpl implements BuildingController {
    @Autowired
    BuildingService buildingService;

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> createBuilding(BuildingRequest buildingRequest) {
        return buildingService.createBuilding(buildingRequest);
    }

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> updateBuilding(@RequestBody BuildingRequest buildingRequest, @PathVariable int buildingId) {
        return buildingService.updateBuilding(buildingRequest, buildingId);
    }

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> deleteBuilding(@PathVariable int buildingId) {
        return buildingService.deleteBuilding(buildingId);
    }

    @Override
    public BaseResponse<BuildingResponse> deleteMultipleBuildings(@RequestParam List<Integer> buildingIds) {
        buildingService.deleteMultipleBuildings(buildingIds);
        return BaseResponse.success(HttpStatus.OK, "Successfully deleted", null);
    }

    @Override
    public BaseResponse<BuildingResponsePagingDTO> findByName(@RequestParam(name = "buildingName") String buildingName,
                                                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                                              @RequestParam(name = "size", required = false, defaultValue = "5") Integer pageSize,
                                                              @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                                                              @RequestParam(name = "order", required = false,  defaultValue = "asc") String sortType) {
        BuildingResponsePagingDTO responsePagingDTO = buildingService.searchBuilding(buildingName, pageNo, pageSize, sortBy, sortType);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, responsePagingDTO);
    }

    @Override
    public BaseResponse<BuildingResponsePagingDTO> getAllBuildings(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                                                   @RequestParam(name = "size", required = false,  defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                                                                   @RequestParam(name = "order", required = false,  defaultValue = "asc") String sortType) {
        BuildingResponsePagingDTO responsePagingDTO = buildingService.getAllBuildings(pageNo, pageSize, sortBy, sortType);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, responsePagingDTO);
    }

    @Override
    public BaseResponse<List<BuildingResponse>> getEnabledBuildings() {
        List<BuildingResponse> responses = buildingService.findAllEnabledBuildings();
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, responses);
    }
}
