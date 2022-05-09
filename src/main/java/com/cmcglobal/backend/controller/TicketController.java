package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.request.ticket.ReviewTicketRequest;
import com.cmcglobal.backend.dto.request.ticket.TicketCreateRequest;
import com.cmcglobal.backend.dto.response.ticket.GetTicketResponse;
import com.cmcglobal.backend.dto.response.ticket.TicketDetail;
import com.cmcglobal.backend.utility.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/ticket")
@Tag(name = "Ticket API", description = "CRUD Ticket")
public interface TicketController {

    @Operation(
            summary = "API create ticket",
            description = "Tạo ra ticket để admin approve",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<GetTicketResponse>> getTicketList(@RequestParam(defaultValue = "0") Integer buildingId,
                                                                  @RequestParam List<Integer> floorIds,
                                                                  @RequestParam String department,
                                                                  @RequestParam String username,
                                                                  @RequestParam String date,
                                                                  @RequestParam(required = false) String role,
                                                                  @RequestParam List<String> status,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer size);

    @Operation(
            summary = "API delete ticket",
            description = "Xoá Ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @DeleteMapping("{id}")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> deleteTicket(@PathVariable Integer id);

    @Operation(
            summary = "API create ticket",
            description = "Tạo ra ticket để admin approve",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PostMapping("")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> createTicketOrderDot(@RequestBody TicketCreateRequest ticketCreateRequest);

    @Operation(
            summary = "API create ticket",
            description = "Tạo ra ticket để admin approve",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PutMapping("")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<String>> reviewTicket(@RequestBody ReviewTicketRequest reviewTicketRequest);

    @Operation(
            summary = "API để review hình ảnh ticket",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @GetMapping("/detail/{ticketId}")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BaseResponse<TicketDetail>> viewTicketDetail(@PathVariable Integer ticketId);
}
