package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.controller.DotController;
import com.cmcglobal.backend.dto.request.dot.UpdateDotListRequest;
import com.cmcglobal.backend.dto.request.dot.UpdateDotMapRequest;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.GetDotResponse;
import com.cmcglobal.backend.service.DotService;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DotControllerImpl implements DotController {

    @Autowired
    private DotService dotService;

    @Override
    public ResponseEntity<BaseResponse<List<DotDTO>>> getListDot(Integer floorId, String department, String username, String date) {
        return dotService.getListDot(floorId, department, username, date);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateUsernameAndStatus(UpdateDotMapRequest updateDotMapRequest) {
        return dotService.updateUsernameAndStatus(updateDotMapRequest);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> resetDot(Integer dotId) {
        return dotService.resetDot(dotId);
    }

    @Override
    public ResponseEntity<BaseResponse<GetDotResponse>> getListDot(Integer buildingId, List<Integer> floorIds, String department, String username, String date, List<String> status, Integer page, Integer size) {
        return dotService.findDots(buildingId, floorIds, department, username, date, status, page, size);
    }

    @Override
    public ResponseEntity<BaseResponse<List<DotDTO>>> searchDotOccupied(String username) {
        return dotService.findDotOccupiedByUsername(username);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> swapDot(Integer id1, Integer id2) {
        return dotService.swapDot(id1, id2);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateStatusDots(UpdateDotListRequest updateDotListRequest) {
        return dotService.updateStatusDots(updateDotListRequest);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateDotEnable(Integer dotId) {
        return dotService.updateDotEnable(dotId);
    }
}
