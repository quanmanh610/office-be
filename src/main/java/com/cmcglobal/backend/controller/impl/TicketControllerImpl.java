package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.controller.TicketController;
import com.cmcglobal.backend.dto.request.ticket.ReviewTicketRequest;
import com.cmcglobal.backend.dto.request.ticket.TicketCreateRequest;
import com.cmcglobal.backend.dto.response.ticket.GetTicketResponse;
import com.cmcglobal.backend.dto.response.ticket.TicketDetail;
import com.cmcglobal.backend.service.TicketService;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketControllerImpl implements TicketController {

    @Autowired
    private TicketService ticketService;

    @Override
    public ResponseEntity<BaseResponse<GetTicketResponse>> getTicketList(Integer buildingId, List<Integer> floorIds, String department, String username, String date, String role, List<String> status, Integer page, Integer size) {
        return ticketService.getTicketList(buildingId, floorIds, department, date, username, role, status, page, size);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> deleteTicket(Integer id) {
        return ticketService.deleteTicket(id);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> createTicketOrderDot(TicketCreateRequest ticketCreateRequest) {
        return ticketService.createTicketOrderDot(ticketCreateRequest);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> reviewTicket(ReviewTicketRequest reviewTicketRequest) {
        return ticketService.reviewTicket(reviewTicketRequest);
    }

    @Override
    public ResponseEntity<BaseResponse<TicketDetail>> viewTicketDetail(Integer ticketId) {
        return ticketService.viewTicket(ticketId);
    }
}