package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketResponse;
import com.aquadrat.parkplatzverwaltung.service.TicketService;
import com.aquadrat.parkplatzverwaltung.support.TicketTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    private TicketService ticketService;
    private TicketController ticketController;

    @BeforeEach
    public void setUp() {
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService);
    }

    @Test
    public void createTicketTest() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        TicketDto ticketDto = TicketTestSupport.generateTicketDto(1);
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(ticketDto, true, HttpStatus.OK.toString());
        when(ticketService.createTicket(ticketCreateRequest)).thenReturn(ticketDto);
        ResponseEntity<TicketResponse> response = ticketController.createTicket(ticketCreateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).createTicket(ticketCreateRequest);
    }

    @Test
    public void createTicketTest_whenNotAvailableExceptionIsThrown() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        when(ticketService.createTicket(ticketCreateRequest)).thenThrow(new NotAvailableException("Message"));
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(null , false, "Message");
        ResponseEntity<TicketResponse> response = ticketController.createTicket(ticketCreateRequest);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).createTicket(ticketCreateRequest);
    }

    @Test
    public void createTicketTest_whenNotFoundExceptionIsThrown() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        when(ticketService.createTicket(ticketCreateRequest)).thenThrow(new NotFoundException("Message"));
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(null , false, "Message");
        ResponseEntity<TicketResponse> response = ticketController.createTicket(ticketCreateRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).createTicket(ticketCreateRequest);
    }

    @Test
    public void getAllTest() {
        List<TicketDto> ticketDtoList = TicketTestSupport.generateTicketDtoList(5);
        when(ticketService.getAll()).thenReturn(ticketDtoList);
        ResponseEntity<List<TicketDto>> response = ticketController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), ticketDtoList);
        verify(ticketService).getAll();
    }

    @Test
    public void parkOutTest() {
        TicketDto ticketDto = TicketTestSupport.generateTicketDto(1);
        when(ticketService.parkOut(1)).thenReturn(ticketDto);
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(ticketDto, true, HttpStatus.OK.toString());
        ResponseEntity<TicketResponse> response = ticketController.parkOut(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).parkOut(1);
    }

    @Test
    public void parkOutTest_whenNotFoundExceptionIsThrown() {
        when(ticketService.parkOut(1)).thenThrow(new NotFoundException("Message"));
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(null, false, "Message");
        ResponseEntity<TicketResponse> response = ticketController.parkOut(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).parkOut(1);
    }

    @Test
    public void parkOutTest_whenNotAvailableExceptionIsThrown() {
        when(ticketService.parkOut(1)).thenThrow(new NotAvailableException("Message"));
        TicketResponse ticketResponse = TicketTestSupport.generateTicketResponse(null, false, "Message");
        ResponseEntity<TicketResponse> response = ticketController.parkOut(1);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(response.getBody(), ticketResponse);
        verify(ticketService).parkOut(1);
    }
}
