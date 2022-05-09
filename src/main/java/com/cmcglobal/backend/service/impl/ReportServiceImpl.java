package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.excel.DotExcel;
import com.cmcglobal.backend.mapper.dot.DotResponseMapper;
import com.cmcglobal.backend.repository.DotRepository;
import com.cmcglobal.backend.service.ReportService;
import com.cmcglobal.backend.utility.BaseResponse;
import com.cmcglobal.backend.utility.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl extends BaseService implements ReportService {

    @Autowired
    private DotRepository dotRepository;

    @Autowired
    private DotResponseMapper dotResponseMapper;

    @Override
    public void export(HttpServletResponse response, Integer buildingId, List<Integer> floorIds, String department, String username, String fromDate, String toDate, List<String> status) throws IOException {
        floorIds = super.getFloorIdByBuilding(floorIds, buildingId);
        List<String> userIdInGroup = super.getUserIdInGroup(department);
        String userId = super.getUsername(username);
        fromDate = fromDate.equals("") ? null : fromDate;
        toDate = toDate.equals("") ? null : toDate;
        List<Dot> dots = dotRepository.findDotsReport(floorIds, userIdInGroup, userId, fromDate, toDate, status);
        List<ReportDotResponse.DotDTO> dotDTOs = dotResponseMapper.toListDotReportDTO(dots);

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=dot.xlsx";
        response.setHeader(headerKey, headerValue);
        DotExcel excelExporter = new DotExcel(dotDTOs, fromDate, toDate);
        excelExporter.export(response);
    }

    @Override
    public ResponseEntity<BaseResponse<ReportDotResponse>> findReportDots(Integer buildingId, List<Integer> floorIds, String department, String username, String fromDate, String toDate, List<String> status, Integer page, Integer size) {
        floorIds = super.getFloorIdByBuilding(floorIds, buildingId);
        List<String> userIdInGroup = getUserIdInGroup(department);
        String userId = getUsername(username);
        fromDate = fromDate.equals("") ? null : fromDate;
        toDate = toDate.equals("") ? null : toDate;
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Dot> dotResult = dotRepository.findDotsReport(floorIds, userIdInGroup, userId, fromDate, toDate, status, paging);
        List<ReportDotResponse.DotDTO> dotListDTO = dotResponseMapper.toListDotReportDTO(dotResult.getContent());
        return ResponseFactory.success(HttpStatus.OK, new ReportDotResponse(dotListDTO, Metadata.build(dotResult)), ErrorMessage.SUCCESS);
    }
}
