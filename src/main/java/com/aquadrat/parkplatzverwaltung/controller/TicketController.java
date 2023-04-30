package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketResponse;
import com.aquadrat.parkplatzverwaltung.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createParkingLot(@RequestBody TicketCreateRequest request) {
        TicketResponse ticketResponse = new TicketResponse();
        try {
            TicketDto ticketDto = ticketService.createTicket(request);
            ticketResponse.setTicketDto(ticketDto);
            ticketResponse.setMessage(HttpStatus.OK.toString());
            ticketResponse.setSuccess(true);
            return ResponseEntity.ok(ticketResponse);
        } catch (NotAvailableException notAvailableException){
            ticketResponse.setMessage(notAvailableException.getMessage());
            ticketResponse.setSuccess(false);
            return new ResponseEntity<>(ticketResponse, HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
