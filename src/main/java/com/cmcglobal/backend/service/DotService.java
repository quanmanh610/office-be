package com.cmcglobal.backend.service;

import com.cmcglobal.backend.dto.request.dot.UpdateDotListRequest;
import com.cmcglobal.backend.dto.request.dot.UpdateDotMapRequest;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.GetDotResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DotService {

    ResponseEntity<BaseResponse<List<DotDTO>>> getListDot(Integer floorId, String department, String username, String date);

    ResponseEntity<BaseResponse<String>> deleteDot(Integer dotId);

    ResponseEntity<BaseResponse<GetDotResponse>> findDots(Integer buildingId, List<Integer> floorIds, String department, String username, String date, List<String> status, Integer page, Integer size);

    ResponseEntity<BaseResponse<String>> updateStatusDots(UpdateDotListRequest updateDotListRequest);

    ResponseEntity<BaseResponse<String>> updateUsernameAndStatus(UpdateDotMapRequest updateDotMapRequest);

    ResponseEntity<BaseResponse<String>> resetDot(Integer dotId);

    ResponseEntity<BaseResponse<List<DotDTO>>> findDotOccupiedByUsername(String username);

    ResponseEntity<BaseResponse<String>> swapDot(Integer id, Integer id2);

    ResponseEntity<BaseResponse<String>> updateDotEnable(Integer dotId);
}
