package com.cmcglobal.backend.exception;

import com.cmcglobal.backend.dto.response.Metadata;
import com.cmcglobal.backend.dto.response.ticket.GetTicketResponse;
import com.cmcglobal.backend.utility.BaseResponse;
import com.cmcglobal.backend.utility.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class OfficeExceptionHandler {
    @ExceptionHandler({TicketException.class})
    public ResponseEntity<BaseResponse<GetTicketResponse>> handleTicketException(TicketException exception) {
        return ResponseFactory.error(HttpStatus.OK, new GetTicketResponse(new ArrayList<>(), Metadata.buildEmpty()), exception.getMessage());
    }
}