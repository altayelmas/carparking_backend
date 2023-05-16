package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketResponse;
import com.aquadrat.parkplatzverwaltung.service.TicketService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketCreateRequest request) {
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
        } catch (NotFoundException notFoundException) {
            ticketResponse.setMessage(notFoundException.getMessage());
            ticketResponse.setSuccess(false);
            return new ResponseEntity<>(ticketResponse, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TicketDto>> getAll() {
        return new ResponseEntity<>(ticketService.getAll(), HttpStatus.OK);
    }

    @PatchMapping("/{ticketID}")
    public ResponseEntity<TicketResponse> parkOut(@PathVariable Integer ticketID) {
        TicketResponse ticketResponse = new TicketResponse();
        try {
            TicketDto ticketDto = ticketService.parkOut(ticketID);
            ticketResponse.setTicketDto(ticketDto);
            ticketResponse.setMessage(HttpStatus.OK.toString());
            ticketResponse.setSuccess(true);
            return ResponseEntity.ok(ticketResponse);
        } catch (NotFoundException notFoundException) {
            ticketResponse.setMessage(notFoundException.getMessage());
            ticketResponse.setSuccess(false);
            return new ResponseEntity<>(ticketResponse, HttpStatus.NOT_FOUND);
        } catch (NotAvailableException notAvailableException) {
            ticketResponse.setMessage(notAvailableException.getMessage());
            ticketResponse.setSuccess(false);
            return new ResponseEntity<>(ticketResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
