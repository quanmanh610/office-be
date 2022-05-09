package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.Constant;
import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.dto.request.dot.UpdateDotListRequest;
import com.cmcglobal.backend.dto.request.dot.UpdateDotMapRequest;
import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.GetDotResponse;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.mapper.dot.DotResponseMapper;
import com.cmcglobal.backend.repository.DotRepository;
import com.cmcglobal.backend.service.DotService;
import com.cmcglobal.backend.utility.BaseResponse;
import com.cmcglobal.backend.utility.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DotServiceImpl extends BaseService implements DotService {
    @Autowired
    private DotRepository dotRepository;

    @Autowired
    private DotResponseMapper dotResponseMapper;

    @Override
    public ResponseEntity<BaseResponse<List<DotDTO>>> getListDot(Integer floorId, String department, String username, String date) {
        List<String> userIdInGroup = super.getUserIdInGroup(department);
        String userId = super.getUsername(username);
        String dateFilter = super.getParseDate(date);
        List<Dot> dotList = dotRepository.findDotsByConditions(floorId, userIdInGroup, dateFilter, userId);
        return ResponseFactory.success(HttpStatus.OK, dotResponseMapper.toListDotDTO(dotList), ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> deleteDot(Integer dotId) {
        if (dotRepository.existsById(dotId)) {
            dotRepository.deleteById(dotId);
            return ResponseFactory.success(HttpStatus.OK, ErrorMessage.DELETE_DOT_SUCCESSFULLY, ErrorMessage.SUCCESS);
        }
        return ResponseFactory.error(HttpStatus.OK, ErrorMessage.DELETE_DOT_FAIL, ErrorMessage.FAILED);
    }

    @Override
    public ResponseEntity<BaseResponse<GetDotResponse>> findDots(Integer buildingId, List<Integer> floorIds, String department, String username, String date, List<String> status, Integer page, Integer size) {
        floorIds = super.getFloorIdByBuilding(floorIds, buildingId);
        List<String> userIdInGroup = super.getUserIdInGroup(department);
        String userId = super.getUsername(username);
        String dateFilter = super.getParseDate(date);
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Dot> dotResult = dotRepository.findDots(floorIds, userIdInGroup, userId, dateFilter, status, paging);
        List<DotDTO> dotListDTO = dotResponseMapper.toListDotDTO(dotResult.getContent());
        return ResponseFactory.success(HttpStatus.OK, new GetDotResponse(dotListDTO, Metadata.build(dotResult)), ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateStatusDots(UpdateDotListRequest request) {
        if (request.getStatus().equals(Constant.StatusType.AVAILABLE)) {
            dotRepository.resetDots(request.getId());
        } else {
            UserFlattened userLog = poaService.getUserInfoByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if (userLog == null) {
                return ResponseFactory.success(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND, ErrorMessage.FAILED);
            }
            dotRepository.updateDateRangeAndStatus(userLog.getUserName(), request.getFromDate(), request.getToDate(), request.getStatus(), request.getId());
        }
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.UPDATE_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateUsernameAndStatus(UpdateDotMapRequest request) {
        if (!dotRepository.existsById(request.getId())) {
            log.error("Id dot doesn't exist: {}", request.getId());
            return ResponseFactory.error(HttpStatus.OK, ErrorMessage.UPDATE_FAILED, ErrorMessage.FAILED);
        }
        if (dotRepository.existsByMember(request.getUsername())) {
            log.error("This user already has a seat:{}", request.getUsername());
            return ResponseFactory.error(HttpStatus.OK, ErrorMessage.UPDATE_FAILED, ErrorMessage.SEAT_EXISTED);
        }
        UserFlattened user = poaService.getUserInfoByUsername(request.getUsername());
        if (user == null) {
            return ResponseFactory.error(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND, ErrorMessage.FAILED);
        }
        dotRepository.updateUserAndStatus(user.getUserName(), request.getStatus(), request.getId());
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.UPDATE_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> resetDot(Integer dotId) {
        dotRepository.resetDots(Collections.singletonList(dotId));
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.DELETE_DOT_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<List<DotDTO>>> findDotOccupiedByUsername(String username) {
        List<UserFlattened> users = poaService.getListUserInfoByUsername(username);
        List<String> usernames = users.stream().map(UserFlattened::getUserName).collect(Collectors.toList());
        List<Dot> dots = dotRepository.findAllByMemberInAndStatusIs(usernames, Constant.StatusType.OCCUPIED);
        return ResponseFactory.success(HttpStatus.OK, dotResponseMapper.toListDotDTO(dots), ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> swapDot(Integer id1, Integer id2) {
        Dot dot1 = dotRepository.findById(id1).orElseThrow(() -> new NotFoundException("Dot id not found:" + id1));
        Dot dot2 = dotRepository.findById(id2).orElseThrow(() -> new NotFoundException("Dot id not found:" + id2));
        if (dot1.getMember() == null || dot2.getMember() == null) {
            throw new BadRequestException("These dot don't have member");
        }
        String member1 = dot1.getMember();
        String member2 = dot2.getMember();
        dot1.setMember(member2);
        dotRepository.save(dot1);
        dot2.setMember(member1);
        dotRepository.save(dot2);

        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.SWAP_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> updateDotEnable(Integer dotId) {
        Dot updatingDot = dotRepository.getOne(dotId);
        updatingDot.setIsActive(!updatingDot.getIsActive());
        dotRepository.save(updatingDot);
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.UPDATE_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }
}
