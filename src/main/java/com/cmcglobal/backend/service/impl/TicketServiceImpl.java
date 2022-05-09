package com.cmcglobal.backend.service.impl;

import com.cmcglobal.backend.constant.ErrorMessage;
import com.cmcglobal.backend.dto.request.ticket.ReviewTicketRequest;
import com.cmcglobal.backend.dto.request.ticket.TicketCreateRequest;
import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.ticket.GetTicketResponse;
import com.cmcglobal.backend.dto.response.ticket.TicketDetail;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.Floor;
import com.cmcglobal.backend.entity.Ticket;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.entity.immutable.TicketDotView;
import com.cmcglobal.backend.exception.TicketException;
import com.cmcglobal.backend.mapper.dot.DotResponseMapper;
import com.cmcglobal.backend.mapper.ticket.TicketDotResponseMapper;
import com.cmcglobal.backend.mapper.ticket.TicketRequestMapper;
import com.cmcglobal.backend.repository.DotRepository;
import com.cmcglobal.backend.repository.TicketDotViewRepository;
import com.cmcglobal.backend.repository.TicketRepository;
import com.cmcglobal.backend.service.TicketService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmcglobal.backend.constant.Constant.*;

@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class, TicketException.class})
public class TicketServiceImpl extends BaseService implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DotRepository dotRepository;

    @Autowired
    private TicketDotViewRepository ticketDotViewRepository;

    @Autowired
    private TicketRequestMapper ticketRequestMapper;

    @Autowired
    private TicketDotResponseMapper ticketDotResponseMapper;

    @Autowired
    private DotResponseMapper dotResponseMapper;

    @Override
    public ResponseEntity<BaseResponse<String>> createTicketOrderDot(TicketCreateRequest request) {
        if (dotRepository.existsByMemberAndStatus(request.getOwner(), StatusType.OCCUPIED)) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, ErrorMessage.EXISTED_MEMBER_OCCUPIED, ErrorMessage.EXISTED_MEMBER_OCCUPIED);
        }
        List<Dot> dotList = dotRepository.findAllById(request.getDotIds());
        Ticket ticket = ticketRequestMapper.toEntity(request);
        UserFlattened user = poaService.getUserInfoByUsername(request.getOwner());
        String username = user.getUserName();
        ticket.setOwner(username);
        ticket.setCreatedBy(username);
        ticket.setUpdatedBy(username);
        ticket.setDots(dotList);
        ticketRepository.save(ticket);
        return ResponseFactory.success(HttpStatus.CREATED, ErrorMessage.CREATE_TICKET_SUCCESS, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<GetTicketResponse>> getTicketList(Integer buildingId, List<Integer> floorIds, String department, String date, String username, String role, List<String> status, Integer page, Integer size) {
        floorIds = getFloorIdByBuilding(floorIds, buildingId);
        List<String> managersUsername = new ArrayList<>();
        String adminUsername = null;
        if (Role.ADMIN.equals(role)) {
            if (username.equals("")) {
                adminUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            managersUsername = getManagersUsername();
        }
        List<String> userIdInGroup = getUserIdInGroup(department);
        username = getUsername(username);
        String dateFilter = getParseDate(date);

        Pageable paging = PageRequest.of(page - 1, size);
        Page<TicketDotView> ticketDots = ticketDotViewRepository.findTicketsByConditions(floorIds, userIdInGroup, managersUsername, username, adminUsername, dateFilter, status, paging);

        return ResponseFactory.success(HttpStatus.OK, new GetTicketResponse(ticketDotResponseMapper.toListDTO(ticketDots.getContent()), Metadata.build(ticketDots)), ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> reviewTicket(ReviewTicketRequest request) {
        List<Integer> ticketIds = request.getTickets().stream().map(ReviewTicketRequest.TicketDTO::getId).collect(Collectors.toList());
        List<Ticket> tickets = ticketRepository.findAllById(ticketIds);
        if (tickets.size() != ticketIds.size()) {
            return ResponseFactory.success(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND, ErrorMessage.FAILED);
        }
        ticketRepository.updateTickets(ticketIds, request.getAction());
        if (Action.APPROVE.equals(request.getAction())) {
            for (ReviewTicketRequest.TicketDTO ticketDTO : request.getTickets()) {
                Ticket ticket = ticketRepository.getTicketByID(ticketDTO.getId());
                List<Integer> dotIds = ticket.getDots().stream().map(Dot::getId).collect(Collectors.toList());
                switch (ticketDTO.getType()) {
                    case TicketType.BOOK:
                        dotRepository.updateOwnerUserAndStatus(ticket.getOwner(), StatusType.ALLOCATED, dotIds, ticket.getFromDate().toString(), ticket.getToDate().toString());
                        break;
                    case TicketType.CLAIM:
                        dotRepository.updateUserAndStatus(ticket.getOwner(), StatusType.OCCUPIED, dotIds, ticket.getFromDate().toString(), ticket.getToDate().toString());
                        break;
                    case TicketType.EXTEND:
                        dotRepository.updateDateRange(dotIds, ticket.getFromDate().toString(), ticket.getToDate().toString());
                        break;
                    default:
                        log.error("Request has type: {}", ticketDTO.getType());
                        throw new BadRequestException();
                }
            }
        }
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.SUCCESSFULLY, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<TicketDetail>> viewTicket(Integer ticketId) {
        //Get ticket by ID
        Ticket ticket = ticketRepository.getOne(ticketId);

        //Send data to client
        TicketDetail ticketDetail = new TicketDetail();

        // Get all dots by ticket
        List<Dot> dots = ticket.getDots();
        ticketDetail.setDotDTOList(dotResponseMapper.toListDotDTO(dots));

        //Get floor background
        Floor floor = floorRepository.getFloorByDotListIn(ticket.getDots());
        ticketDetail.setFloorBackground(floor.getBackgroundFloor());
        return ResponseFactory.success(HttpStatus.OK, ticketDetail, ErrorMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<BaseResponse<String>> deleteTicket(Integer id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketException(ErrorMessage.TICKET_NOT_FOUND);
        }
        ticketRepository.deleteById(id);
        return ResponseFactory.success(HttpStatus.OK, ErrorMessage.DELETE_TICKET_SUCCESSFULLY, ErrorMessage.SUCCESS);
    }
}
