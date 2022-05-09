package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/report")
public interface ReportController {
    @Operation(
            summary = "API export dot by month",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("dot")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<ReportDotResponse>> reportDots(@RequestParam(defaultValue = "0") Integer buildingId,
                                                               @RequestParam List<Integer> floorIds,
                                                               @RequestParam String department,
                                                               @RequestParam String username,
                                                               @RequestParam String fromDate,
                                                               @RequestParam String toDate,
                                                               @RequestParam List<String> status,
                                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(required = false, defaultValue = "10") Integer size);

    @Operation(
            summary = "API export dot by month",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("dot/export")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    void export(HttpServletResponse response,
                @RequestParam(defaultValue = "0") Integer buildingId,
                @RequestParam List<Integer> floorIds,
                @RequestParam String department,
                @RequestParam String username,
                @RequestParam String fromDate,
                @RequestParam String toDate,
                @RequestParam List<String> status) throws IOException;
}
