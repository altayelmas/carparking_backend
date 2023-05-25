package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.mapper.TicketMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import com.aquadrat.parkplatzverwaltung.repository.VehicleRepository;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import com.aquadrat.parkplatzverwaltung.support.TicketTestSupport;
import com.aquadrat.parkplatzverwaltung.support.VehicleTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;
    private TicketMapper ticketMapper;

    @BeforeEach
    public void setUp() {
        ticketRepository = mock(TicketRepository.class);
        parkingLotRepository = mock(ParkingLotRepository.class);
        vehicleRepository = mock(VehicleRepository.class);
        ticketMapper = mock(TicketMapper.class);

        ticketService = new TicketService(ticketRepository, parkingLotRepository, vehicleRepository, ticketMapper);
    }

    @Test
    public void getAllTest() {
        List<Ticket> ticketList = TicketTestSupport.generateTicketList(3);
        List<TicketDto> ticketDtoList = TicketTestSupport.generateTicketDtoList(3);
        when(ticketRepository.findAll()).thenReturn(ticketList);
        when(ticketMapper.ticketListToTicketDtoList(ticketList)).thenReturn(ticketDtoList);

        List<TicketDto> result = ticketService.getAll();
        assertEquals(ticketDtoList, result);
        verify(ticketRepository).findAll();
        verify(ticketMapper).ticketListToTicketDtoList(ticketList);

    }

    @Test
    public void createTicketTest_whenParkingLotDoesNotExist_shouldThrowNotFoundException() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        when(parkingLotRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verify(parkingLotRepository).findById(5);
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenParkSlotDoesNotExist_shouldThrowNotFoundException() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotFoundException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenParkSlotIsNotAvailable_shouldThrowNotAvailableException() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        ticketCreateRequest.setSlotID(15);
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        parkingLot.getParkSlots().get(15).setAvailable(false);

        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotAvailableException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenVehicleIsAlreadyInParkingLot_shouldThrowNotAvailableException() {
        Vehicle vehicle = VehicleTestSupport.generateVehicle();
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        ticketCreateRequest.setSlotID(15);
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        parkingLot.getParkSlots().get(3).setAvailable(false);
        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById(vehicle.getLicencePlate())).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findTicketByVehicleAndIsValid(vehicle, true)).thenReturn(vehicle.getTicketList().get(3));
        assertThrows(NotAvailableException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest() {
        TicketCreateRequest ticketCreateRequest = TicketTestSupport.generateTicketCreateRequest();
        ticketCreateRequest.setSlotID(15);
        Vehicle vehicle = VehicleTestSupport.generateVehicle();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        TicketDto ticketDto = TicketTestSupport.generateTicketDto(3);
        ticketDto.setSlotID(15);
        ticketDto.setLotID(5);
        Ticket ticket = TicketTestSupport.generateTicket(3);
        ticket.setParkSlot(parkingLot.getParkSlots().get(15));
        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById(vehicle.getLicencePlate())).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findTicketByVehicleAndIsValid(vehicle, true)).thenReturn(null);
        when(ticketRepository.save(any())).thenReturn(ticket);
        when(ticketMapper.convertToDto(ticket)).thenReturn(ticketDto);

        TicketDto result = ticketService.createTicket(ticketCreateRequest);
        assertEquals(ticketDto, result);
    }
}
