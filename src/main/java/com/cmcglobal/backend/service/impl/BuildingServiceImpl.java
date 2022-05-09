package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.dto.request.building.BuildingRequest;
import com.cmcglobal.backend.dto.response.BuildingResponsePagingDTO;
import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.building.BuildingResponse;
import com.cmcglobal.backend.entity.Building;
import com.cmcglobal.backend.mapper.BuildingRequestMapper;
import com.cmcglobal.backend.mapper.BuildingResponseMapper;
import com.cmcglobal.backend.repository.BuildingRepository;
import com.cmcglobal.backend.repository.FloorRepository;
import com.cmcglobal.backend.service.BuildingService;
import com.cmcglobal.backend.utility.BaseResponse;
import com.cmcglobal.backend.utility.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    BuildingResponseMapper buildingResponseMapper;

    @Autowired
    BuildingRequestMapper buildingRequestMapper;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    FloorRepository floorRepository;

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> createBuilding(BuildingRequest buildingRequest) {
        if (buildingRepository.existsAllByBuildingAddress(buildingRequest.getBuildingAddress())) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, ErrorMessage.BUILDING_ADDRESS_EXIST);
        }
        if (buildingRepository.existsAllByBuildingName(buildingRequest.getBuildingName())) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, ErrorMessage.BUILDING_NAME_EXIST);
        }
        Building building = buildingRequestMapper.toEntity(buildingRequest);
        buildingRepository.save(building);
        return ResponseFactory.success(HttpStatus.CREATED, buildingResponseMapper.toDTO(building), ErrorMessage.BUILDING_CREATED_SUCCESSFULLY);
    }

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> updateBuilding(BuildingRequest buildingRequest, int buildingId) {
        if (buildingRepository.existsAllByBuildingAddressAndIdIsNot(buildingRequest.getBuildingAddress(), buildingId)) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, ErrorMessage.BUILDING_ADDRESS_EXIST);
        }
        if (buildingRepository.existsAllByBuildingNameAndIdIsNot(buildingRequest.getBuildingName(), buildingId)) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, ErrorMessage.BUILDING_NAME_EXIST);
        }
        Building editingBuilding = buildingRepository.getOne(buildingId);
        //Set fields
        editingBuilding.setBuildingName(buildingRequest.getBuildingName());
        editingBuilding.setBuildingAddress(buildingRequest.getBuildingAddress());
        editingBuilding.setIsEnable(buildingRequest.getIsEnable());
        Building building = buildingRepository.save(editingBuilding);
        return ResponseFactory.success(HttpStatus.OK, buildingResponseMapper.toDTO(building), ErrorMessage.UPDATE_SUCCESSFULLY);
    }

    @Override
    public ResponseEntity<BaseResponse<BuildingResponse>> deleteBuilding(int buildingId) {
        Building building = buildingRepository.getOne(buildingId);
        if (!buildingRepository.existsById(buildingId)){
            throw new NotFoundException(ErrorMessage.NOT_FOUND);
        }
        buildingRepository.deleteById(buildingId);
        return ResponseFactory.success(HttpStatus.OK, buildingResponseMapper.toDTO(building), ErrorMessage.UPDATE_SUCCESSFULLY);
    }

    @Override
    public void deleteMultipleBuildings(List<Integer> buildingIds) {
        List<Building> buildingsToBeDeleted = buildingRepository.findAllById(buildingIds);
        buildingRepository.deleteInBatch(buildingsToBeDeleted);
    }

    public BuildingResponsePagingDTO getAllBuildings(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize, sortType.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Page<Building> result = buildingRepository.findAll(paging);
        List<BuildingResponse> finalResult = buildingResponseMapper.toListDTO(result.getContent());

        // Set number of floor, room dots and seat dots, price and cost
        for (BuildingResponse buildingResponse : finalResult) {
            Integer numberOfFloorInBuilding = floorRepository.countFloorsByBuilding(buildingResponseMapper.toEntity(buildingResponse));
            buildingResponse.setNumberOfFloor(numberOfFloorInBuilding);
            Integer numberOfRoomDotInBuilding = floorRepository.countRoomDotsInBuilding(buildingResponse.getId());
            Integer numberOfSeatDotInBuilding = floorRepository.countSeatDotsInBuilding(buildingResponse.getId());
            Float buildingCost = floorRepository.sumTotalBuildingCost(buildingResponse.getId());
            Float buildingPrice = floorRepository.sumTotalBuildingPrice(buildingResponse.getId());
            buildingResponse.setNumberOfRoomDot(numberOfRoomDotInBuilding);
            buildingResponse.setNumberOfSeatDot(numberOfSeatDotInBuilding);
            buildingResponse.setCost(buildingCost);
            buildingResponse.setPrice(buildingPrice);
        }

        return new BuildingResponsePagingDTO(finalResult, Metadata.build(result));
    }

    @Override
    public BuildingResponsePagingDTO searchBuilding(String buildingName, Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize, sortType.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Page<Building> result = buildingRepository.findAllByBuildingNameContaining(buildingName, paging);
        List<BuildingResponse> finalResult = buildingResponseMapper.toListDTO(result.getContent());

        // Set number of floor, room dots and seat dots, price and cost
        for (BuildingResponse buildingResponse : finalResult) {
            Integer numberOfFloorInBuilding = floorRepository.countFloorsByBuilding(buildingResponseMapper.toEntity(buildingResponse));
            buildingResponse.setNumberOfFloor(numberOfFloorInBuilding);
            Integer numberOfRoomDotInBuilding = floorRepository.countRoomDotsInBuilding(buildingResponse.getId());
            Integer numberOfSeatDotInBuilding = floorRepository.countSeatDotsInBuilding(buildingResponse.getId());
            Float buildingCost = floorRepository.sumTotalBuildingCost(buildingResponse.getId());
            Float buildingPrice = floorRepository.sumTotalBuildingPrice(buildingResponse.getId());
            buildingResponse.setNumberOfRoomDot(numberOfRoomDotInBuilding);
            buildingResponse.setNumberOfSeatDot(numberOfSeatDotInBuilding);
            buildingResponse.setCost(buildingCost);
            buildingResponse.setPrice(buildingPrice);
        }
        return new BuildingResponsePagingDTO(finalResult, Metadata.build(result));
    }

    @Override
    public List<BuildingResponse> findAllEnabledBuildings() {
        List<Building> result = buildingRepository.findAllByIsEnableIsTrueOrderByBuildingNameAsc();
        return buildingResponseMapper.toListDTO(result);
    }
}
