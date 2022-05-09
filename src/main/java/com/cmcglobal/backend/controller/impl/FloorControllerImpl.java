package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.controller.FloorController;
import com.cmcglobal.backend.dto.request.floor.FloorRequest;
import com.cmcglobal.backend.dto.request.floor.FloorUpdateRequest;
import com.cmcglobal.backend.dto.response.FloorResponsePagingDTO;
import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import com.cmcglobal.backend.service.FloorService;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloorControllerImpl implements FloorController {
    @Autowired
    private FloorService floorService;

    @Override
    public ResponseEntity<BaseResponse<String>> createFloor(FloorRequest floorRequest) {
        return floorService.createFloor(floorRequest);
    }

    @Override
    public BaseResponse<FloorResponse> updateFloor(@RequestBody FloorUpdateRequest floorUpdateRequest, @PathVariable Integer floorId) {
        FloorResponse response = floorService.updateFloor(floorUpdateRequest, floorId);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, response);
    }

    @Override
    public BaseResponse<FloorResponse> updateFloorEnable(Integer floorId) {
        floorService.updateFloorEnable(floorId);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, null);
    }

    @Override
    public BaseResponse<FloorResponse> deleteFloor(@PathVariable int floorId) {
        floorService.deleteFloor(floorId);
        return BaseResponse.success(HttpStatus.OK, "Successfully deleted",null);
    }

    @Override
    public BaseResponse<FloorResponsePagingDTO> getFloors(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        FloorResponsePagingDTO result = floorService.getFloors(pageNo, pageSize, sortBy, sortType);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, result);
    }

    @Override
    public BaseResponse<FloorResponsePagingDTO> getFloorSearch(Integer pageNo, Integer pageSize, String sortBy, String sortType, String floorName) {
        FloorResponsePagingDTO response = floorService.getFloorByName(pageNo, pageSize, sortBy, sortType, floorName);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, response);
    }

    @Override
    public BaseResponse<List<FloorResponse>> getEnabledFloors() {
        List<FloorResponse> response = floorService.getEnabledFloors();
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, response);
    }

    @Override
    public BaseResponse<List<FloorResponse>> getEnabledFloorsInBuilding(int buildingId) {
        List<FloorResponse> response = floorService.getEnabledFloorsInBuilding(buildingId);
        return BaseResponse.success(HttpStatus.OK, ErrorMessage.SUCCESS, response);
    }
}
