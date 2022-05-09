package com.cmcglobal.backend.service;

import com.cmcglobal.backend.dto.request.ticket.ReviewTicketRequest;
import com.cmcglobal.backend.dto.request.ticket.TicketCreateRequest;
import com.cmcglobal.backend.dto.response.ticket.GetTicketResponse;
import com.cmcglobal.backend.dto.response.ticket.TicketDetail;
import com.cmcglobal.backend.utility.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<BaseResponse<GetTicketResponse>> getTicketList(Integer buildingId, List<Integer> floorIds, String department, String date, String username, String role, List<String> status, Integer page, Integer size);

    ResponseEntity<BaseResponse<String>> createTicketOrderDot(TicketCreateRequest ticketCreateRequest);

    ResponseEntity<BaseResponse<String>> reviewTicket(ReviewTicketRequest reviewTicketRequest);

    ResponseEntity<BaseResponse<TicketDetail>> viewTicket(Integer ticketId);

    ResponseEntity<BaseResponse<String>> deleteTicket(Integer id);
}
