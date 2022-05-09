package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.request.building.BuildingRequest;
import com.cmcglobal.backend.dto.response.BuildingResponsePagingDTO;
import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/building")
public interface BuildingController {
    @PostMapping
    ResponseEntity<BaseResponse<BuildingResponse>> createBuilding(@RequestBody BuildingRequest buildingRequest);

    @PutMapping("/{buildingId}")
    ResponseEntity<BaseResponse<BuildingResponse>> updateBuilding(@RequestBody BuildingRequest buildingRequest, @PathVariable int buildingId);

    @DeleteMapping("/{buildingId}")
    ResponseEntity<BaseResponse<BuildingResponse>> deleteBuilding(@PathVariable int buildingId);

    @DeleteMapping
    BaseResponse<BuildingResponse> deleteMultipleBuildings(@RequestParam List<Integer> buildingIds);

    @GetMapping("/searching")
    BaseResponse<BuildingResponsePagingDTO> findByName(@RequestParam(name = "buildingName") String buildingName,
                            @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "size", required = false, defaultValue = "5") Integer pageSize,
                            @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                            @RequestParam(name = "order", required = false,  defaultValue = "desc") String sortType);
    @GetMapping
    BaseResponse<BuildingResponsePagingDTO> getAllBuildings(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                                            @RequestParam(name = "size", required = false,  defaultValue = "5") Integer pageSize,
                                                            @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                                                            @RequestParam(name = "order", required = false,  defaultValue = "desc") String sortType);
    @GetMapping("/enabled")
    BaseResponse<List<BuildingResponse>> getEnabledBuildings();
}
