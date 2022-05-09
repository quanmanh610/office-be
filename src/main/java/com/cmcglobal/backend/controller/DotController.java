package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.request.dot.UpdateDotListRequest;
import com.cmcglobal.backend.dto.request.dot.UpdateDotMapRequest;
import com.cmcglobal.backend.dto.response.dot.DotDTO;
import com.cmcglobal.backend.dto.response.dot.GetDotResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("api/v1/dot")
public interface DotController {

    @Operation(
            summary = "API get a list of dot of one floor (screen map)",
            description = "Get a list of all dot or get list dot by filter",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("map")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<List<DotDTO>>> getListDot(@RequestParam Integer floorId,
                                                          @RequestParam String department,
                                                          @RequestParam String username,
                                                          @RequestParam String date);

    @Operation(
            summary = "API update username and status on the (screen map)",
            description = "Assign user in the dot and status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PutMapping("list/user-status")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> updateUsernameAndStatus(@RequestBody UpdateDotMapRequest updateDotMapRequest);

    @Operation(
            summary = "API reset dot to status available (screen list)",
            description = "Reset dot",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @DeleteMapping("list/{dotId}")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> resetDot(@PathVariable Integer dotId);


    @Operation(
            summary = "API get a list of all dot (screen list)",
            description = "Get a list of all dot or get list dot by filter",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("list")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<GetDotResponse>> getListDot(@RequestParam(defaultValue = "0") Integer buildingId,
                                                            @RequestParam List<Integer> floorIds,
                                                            @RequestParam String department,
                                                            @RequestParam String username,
                                                            @RequestParam String date,
                                                            @RequestParam List<String> status,
                                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                                            @RequestParam(required = false, defaultValue = "10") Integer size);

    @Operation(
            summary = "API search dot by username",
            description = "Search dot to swap",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("list/{username}")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<List<DotDTO>>> searchDotOccupied(@PathVariable String username);

    @Operation(
            summary = "API swap seat dot username to username",
            description = "Swap seat dot ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PutMapping("list/{id1}/swap/{id2}")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> swapDot(@PathVariable Integer id1, @PathVariable Integer id2);

    @Operation(
            summary = "API update single or multiple time range and status",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PutMapping("list/status-date-range")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> updateStatusDots(@RequestBody UpdateDotListRequest updateDotListRequest);

    @PutMapping("list/enable-switch/{dotId}")
    ResponseEntity<BaseResponse<String>> updateDotEnable(@PathVariable Integer dotId);
}