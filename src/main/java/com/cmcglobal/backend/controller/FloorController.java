package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.request.floor.FloorRequest;
import com.cmcglobal.backend.dto.request.floor.FloorUpdateRequest;
import com.cmcglobal.backend.dto.response.FloorResponsePagingDTO;
import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/floor")
public interface FloorController {
    @PostMapping
    ResponseEntity<BaseResponse<String>> createFloor(@RequestBody FloorRequest floorRequest);

    @PutMapping("/{floorId}")
    BaseResponse<FloorResponse> updateFloor(@RequestBody FloorUpdateRequest floorUpdateRequest, @PathVariable Integer floorId);

    @PutMapping("/enable/{floorId}")
    BaseResponse<FloorResponse> updateFloorEnable(@PathVariable Integer floorId);

    @DeleteMapping("/{floorId}")
    BaseResponse<FloorResponse> deleteFloor(@PathVariable int floorId);

    @GetMapping()
    BaseResponse<FloorResponsePagingDTO> getFloors(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "size", required = false,  defaultValue = "10") Integer pageSize,
                                                   @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                                                   @RequestParam(name = "order", required = false,  defaultValue = "desc") String sortType);

    @GetMapping("/search")
    BaseResponse<FloorResponsePagingDTO> getFloorSearch(@RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "size", required = false,  defaultValue = "10") Integer pageSize,
                                                        @RequestParam(name = "sort", required = false,  defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "order", required = false,  defaultValue = "desc") String sortType,
                                                        @RequestParam(required = false) String floorName);

    @GetMapping("/enabled")
    BaseResponse<List<FloorResponse>> getEnabledFloors();

    @GetMapping("/enabled/{buildingId}")
    BaseResponse<List<FloorResponse>> getEnabledFloorsInBuilding(@PathVariable int buildingId);



}
