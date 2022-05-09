package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.controller.ReportController;
import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.service.ReportService;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ReportControllerImpl implements ReportController {
    @Autowired
    private ReportService reportService;

    @Override
    public ResponseEntity<BaseResponse<ReportDotResponse>> reportDots(Integer buildingId, List<Integer> floorIds, String department, String username, String fromDate, String toDate, List<String> status, Integer page, Integer size) {
        return reportService.findReportDots(buildingId, floorIds, department, username, fromDate, toDate, status, page, size);
    }

    @Override
    public void export(HttpServletResponse response, Integer buildingId, List<Integer> floorIds, String department, String username, String fromDate, String toDate, List<String> status) throws IOException {
        reportService.export(response, buildingId, floorIds, department, username, fromDate, toDate, status);
    }
}
