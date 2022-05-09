package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.Constant;
import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.dto.request.dot.CreateDotRequest;
import com.cmcglobal.backend.dto.request.floor.FloorRequest;
import com.cmcglobal.backend.dto.request.floor.FloorUpdateRequest;
import com.cmcglobal.backend.dto.response.FloorResponsePagingDTO;
import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.floor.FloorResponse;
import com.cmcglobal.backend.entity.Building;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.Floor;
import com.cmcglobal.backend.mapper.FloorMapper;
import com.cmcglobal.backend.mapper.FloorRequestMapper;
import com.cmcglobal.backend.mapper.FloorResponseMapper;
import com.cmcglobal.backend.mapper.FloorUpdateRequestMapper;
import com.cmcglobal.backend.repository.BuildingRepository;
import com.cmcglobal.backend.repository.DotRepository;
import com.cmcglobal.backend.repository.FloorRepository;
import com.cmcglobal.backend.service.FloorService;
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

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    DotRepository dotRepository;

    @Autowired
    FloorMapper floorMapper;

    @Autowired
    FloorMapper dotMapper;

    @Autowired
    FloorResponseMapper floorResponseMapper;

    @Autowired
    FloorRequestMapper floorRequestMapper;

    @Autowired
    FloorUpdateRequestMapper floorUpdateRequestMapper;

    @Override
    public ResponseEntity<BaseResponse<String>> createFloor(FloorRequest floorRequest) {
        Building building = buildingRepository.getOne(floorRequest.getBuildingId());
        if (floorRepository.existsAllByFloorNameAndBuilding(floorRequest.getFloorName(), building)){
            return ResponseFactory.success(HttpStatus.BAD_REQUEST, ErrorMessage.FLOOR_NAME_EXIST, ErrorMessage.FAILED);
        }
        Floor floor = floorRequestMapper.toEntity(floorRequest);
        floor.setBuilding(building);

        // Create dots
        List<Dot> dots = new ArrayList<>();
        for (CreateDotRequest dotDTO: floorRequest.getDots()) {
            Dot dot = new Dot();
            dot.setCoordinateX(dotDTO.getCoordinateX());
            dot.setCoordinateY(dotDTO.getCoordinateY());
            dot.setStatus(dotDTO.getStatus());
            dot.setType(dotDTO.getType());
            dot.setFloor(floor);
            dots.add(dot);
        }
        floor.setDotList(dots);
        floorRepository.save(floor);

        // Set fields number of seat and room dot
        Integer seatDotCount = dotRepository.countDotsByFloorAndType(floor, Constant.DotType.SEAT_DOT);
        Integer roomDotCount = dotRepository.countDotsByFloorAndType(floor, Constant.DotType.ROOM_DOT);
        floor.setNumberOfRoomDot(roomDotCount);
        floor.setNumberOfSeatDot(seatDotCount);

        // Set floor total price
        floor.setPrice((floor.getNumberOfSeatDot() == null || floor.getDotPricePerMonth() == null)? null: floor.getDotPricePerMonth()* floor.getNumberOfSeatDot());
        floorRepository.save(floor);
        return ResponseFactory.success(HttpStatus.CREATED, ErrorMessage.FLOOR_CREATED_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public FloorResponse updateFloor(FloorUpdateRequest floorUpdateRequest, int floorId) {

        Building building = buildingRepository.getOne(floorRepository.getOne(floorId).getBuilding().getId());
        if (floorRepository.existsAllByFloorNameAndBuildingAndIdIsNot(floorUpdateRequest.getFloorName(), building, floorId)){
            throw new BadRequestException();
        }

        Floor updatingFloor = floorRepository.getOne(floorId);
        //set fields
        updatingFloor.setBuilding(buildingRepository.getOne(floorUpdateRequest.getBuildingId()));
        updatingFloor.setFloorName(floorUpdateRequest.getFloorName());
        updatingFloor.setBackgroundFloor(floorUpdateRequest.getBackgroundFloor());
        updatingFloor.setCost(floorUpdateRequest.getCost());
        updatingFloor.setDotPricePerMonth(floorUpdateRequest.getDotPricePerMonth());

        // Create & Update dots
        List<Dot> newDots = new ArrayList<>();
        for (DotDTO dotDTO: floorUpdateRequest.getDots()) {
            // Create
            if (dotDTO.getId() == null){
                Dot dot = new Dot();
                dot.setCoordinateX(dotDTO.getCoordinateX());
                dot.setCoordinateY(dotDTO.getCoordinateY());
                dot.setStatus(dotDTO.getStatus());
                dot.setType(dotDTO.getType());
                dot.setIsActive(dotDTO.getIsActive());
                dot.setFloor(updatingFloor);
                newDots.add(dot);
                // Update
            } else if (dotDTO.getId() != null) {
                Dot updatingDot = dotRepository.getOne(dotDTO.getId());
                updatingDot.setFloor(updatingFloor);
                updatingDot.setCoordinateX(dotDTO.getCoordinateX());
                updatingDot.setCoordinateY(dotDTO.getCoordinateY());
                updatingDot.setStatus(dotDTO.getStatus());
                updatingDot.setType(dotDTO.getType());
                updatingDot.setIsActive(dotDTO.getIsActive());
                dotRepository.save(updatingDot);
            }
        }
        dotRepository.saveAll(newDots);
        floorRepository.save(updatingFloor);

        // Delete dots
        for (Integer deletedDotIds: floorUpdateRequest.getDeleteDots()) {
            dotRepository.deleteById(deletedDotIds);
        }

        // Set fields number of seat and room dot
        Integer seatDotCount = dotRepository.countDotsByFloorAndType(updatingFloor, Constant.DotType.SEAT_DOT);
        Integer roomDotCount = dotRepository.countDotsByFloorAndType(updatingFloor, Constant.DotType.ROOM_DOT);
        updatingFloor.setNumberOfRoomDot(roomDotCount);
        updatingFloor.setNumberOfSeatDot(seatDotCount);

        // Set floor total price
        updatingFloor.setPrice((updatingFloor.getNumberOfSeatDot() == null || updatingFloor.getDotPricePerMonth() == null)? null: updatingFloor.getDotPricePerMonth()* updatingFloor.getNumberOfSeatDot());
        Floor finalResult = floorRepository.save(updatingFloor);
        return floorResponseMapper.toDTO(finalResult);
    }

    @Override
    public void updateFloorEnable(int floorId) {
        Floor updatingFloor = floorRepository.getOne(floorId);
        updatingFloor.setIsEnable(!updatingFloor.getIsEnable());
        floorRepository.save(updatingFloor);
    }

    @Override
    public void deleteFloor(int floorId) {
        floorRepository.deleteById(floorId);
    }

    @Override
    public FloorResponsePagingDTO getFloors(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Pageable paging = PageRequest.of(pageNo -1, pageSize, sortType.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Page<Floor> result = floorRepository.findAll(paging);

        List<FloorResponse> finalResult = new ArrayList<>();
        //Set Building & number of dots info in floor
        for (Floor floor: result) {
            Building building = buildingRepository.getOne(floor.getBuilding().getId());
            FloorResponse floorResponse = floorResponseMapper.toDTO(floor);
            floorResponse.setBuildingId(building.getId());
            floorResponse.setBuildingName(building.getBuildingName());
            floorResponse.setAddress(building.getBuildingAddress());

            finalResult.add(floorResponse);
        }

        return new FloorResponsePagingDTO(finalResult, Metadata.build(result));
    }

    @Override
    public FloorResponsePagingDTO getFloorByName(Integer pageNo, Integer pageSize, String sortBy, String sortType, String floorName) {
        Pageable paging = PageRequest.of(pageNo -1, pageSize, sortType.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Page<Floor> result = floorRepository.findAllByFloorNameContaining(floorName, paging);

        List<FloorResponse> finalResult = new ArrayList<>();
        //Set Building info in floor
        for (Floor floor: result) {
            Building building = buildingRepository.getOne(floor.getBuilding().getId());
            FloorResponse floorResponse = floorResponseMapper.toDTO(floor);
            floorResponse.setBuildingId(building.getId());
            floorResponse.setBuildingName(building.getBuildingName());
            floorResponse.setAddress(building.getBuildingAddress());
            finalResult.add(floorResponse);
        }
        return new FloorResponsePagingDTO(finalResult, Metadata.build(result));
    }

    @Override
    public List<FloorResponse> getEnabledFloors() {
        List<Floor> enabledFloors = floorRepository.findAllByIsEnableIsTrue();
        return floorResponseMapper.toListDTO(enabledFloors);
    }

    @Override
    public List<FloorResponse> getEnabledFloorsInBuilding(int buildingId) {
        Building building = buildingRepository.getOne(buildingId);
        List<Floor> enabledFloorsInBuilding = floorRepository.findAllByIsEnableIsTrueAndBuilding(building);
        List<FloorResponse> finalResult = floorResponseMapper.toListDTO(enabledFloorsInBuilding);
        for (FloorResponse floorResponse: finalResult) {
            floorResponse.setBuildingName(building.getBuildingName());
            floorResponse.setBuildingId(building.getId());
            floorResponse.setAddress(building.getBuildingAddress());
        }
        finalResult.sort(new Comparator<FloorResponse>() {
            @Override
            public int compare(FloorResponse o1, FloorResponse o2) {
                return extractInt(o1.getFloorName()) - extractInt(o2.getFloorName());
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        return finalResult;
    }
}
