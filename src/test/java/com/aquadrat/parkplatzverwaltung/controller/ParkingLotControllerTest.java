package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.exception.SlotsNotEmptyException;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotResponse;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotUpdateRequest;
import com.aquadrat.parkplatzverwaltung.service.ParkingLotService;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingLotControllerTest {

    private ParkingLotService parkingLotService;
    private ParkingLotController parkingLotController;
    @BeforeEach
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        parkingLotController = new ParkingLotController(parkingLotService);
    }

    @Test
    public void createParkingLotTest() {
        ParkingLotCreateRequest parkingLotCreateRequest = ParkingLotTestSupport.generateParkingLotCreateRequest();
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        when(parkingLotService.createParkingLot(parkingLotCreateRequest)).thenReturn(parkingLotDto);
        ResponseEntity<ParkingLotDto> response =  parkingLotController.createParkingLot(parkingLotCreateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotDto);
        verify(parkingLotService).createParkingLot(parkingLotCreateRequest);
    }

    @Test
    public void getAllTest() {
        List<ParkingLotDto> parkingLotDtoList = ParkingLotTestSupport.generateParkingLotDtoList(5);
        when(parkingLotService.getAll()).thenReturn(parkingLotDtoList);
        ResponseEntity<List<ParkingLotDto>> response = parkingLotController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotDtoList);
        verify(parkingLotService).getAll();
    }

    @Test
    public void deleteParkingLotTest() {
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(parkingLotDto, HttpStatus.OK.toString(), true);
        when(parkingLotService.deleteParkingLot(1)).thenReturn(parkingLotDto);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.deleteParkingLot(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        verify(parkingLotService).deleteParkingLot(1);
    }

    @Test
    public void deleteParkingLotTest_whenNotFoundExceptionIsThrown() {
        when(parkingLotService.deleteParkingLot(1)).thenThrow(new NotFoundException("Message"));
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null , "Message", false);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.deleteParkingLot(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).deleteParkingLot(1);
    }

    @Test
    public void deleteParkingLotTest_whenNotAvailableExceptionIsThrown() {
        when(parkingLotService.deleteParkingLot(1)).thenThrow(new NotAvailableException("Message"));
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null , "Message", false);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.deleteParkingLot(1);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).deleteParkingLot(1);
    }

    @Test
    public void updateParkingLotTest() {
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(50);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        when(parkingLotService.updateParkingLot(1, parkingLotUpdateRequest)).thenReturn(parkingLotDto);
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(parkingLotDto, HttpStatus.OK.toString(), true);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.updateParkingLot(1, parkingLotUpdateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        verify(parkingLotService).updateParkingLot(1, parkingLotUpdateRequest);
    }

    @Test
    public void updateParkingLotTest_whenNotFoundExceptionIsThrown() {
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(50);
        when(parkingLotService.updateParkingLot(1, parkingLotUpdateRequest)).thenThrow(new NotFoundException("Message"));
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null , "Message", false);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.updateParkingLot(1, parkingLotUpdateRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).updateParkingLot(1, parkingLotUpdateRequest);
    }

    @Test
    public void updateParkingLotTest_whenSlotsNotEmptyExceptionIsThrown() {
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(50);
        when(parkingLotService.updateParkingLot(1, parkingLotUpdateRequest)).thenThrow(new SlotsNotEmptyException("Message"));
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null , "Message", false);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.updateParkingLot(1, parkingLotUpdateRequest);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).updateParkingLot(1, parkingLotUpdateRequest);
    }

    @Test
    public void updateParkingLotTest_whenNotAvailableExceptionIsThrown() {
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(50);
        when(parkingLotService.updateParkingLot(1, parkingLotUpdateRequest)).thenThrow(new NotAvailableException("Message"));
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null , "Message", false);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.updateParkingLot(1, parkingLotUpdateRequest);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).updateParkingLot(1, parkingLotUpdateRequest);
    }

    @Test
    public void getParkingLotByIdTest() {
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(parkingLotDto, HttpStatus.OK.toString(), true);
        when(parkingLotService.getParkingLotById(1)).thenReturn(parkingLotDto);
        ResponseEntity<ParkingLotResponse> response = parkingLotController.getParkingLotById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals(HttpStatus.OK.toString(), parkingLotResponse.getMessage());
        verify(parkingLotService).getParkingLotById(1);
    }

    @Test
    public void getParkingLotByIdTest_whenNotFoundExceptionIsThrown() {
        ParkingLotResponse parkingLotResponse = ParkingLotTestSupport.generateParkingLotResponse(null, "Message", false);
        when(parkingLotService.getParkingLotById(1)).thenThrow(new NotFoundException("Message"));
        ResponseEntity<ParkingLotResponse> response = parkingLotController.getParkingLotById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), parkingLotResponse);
        assertEquals("Message", parkingLotResponse.getMessage());
        verify(parkingLotService).getParkingLotById(1);
    }


}
