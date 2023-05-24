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
        List<Ticket> ticketList = getTicketList();
        List<TicketDto> ticketDtoList = getTicketDtoList();
        when(ticketRepository.findAll()).thenReturn(ticketList);
        when(ticketMapper.ticketListToTicketDtoList(ticketList)).thenReturn(ticketDtoList);

        List<TicketDto> result = ticketService.getAll();
        assertEquals(ticketDtoList, result);
        verify(ticketRepository).findAll();
        verify(ticketMapper).ticketListToTicketDtoList(ticketList);

    }

    @Test
    public void createTicketTest_whenParkingLotDoesNotExist_shouldThrowNotFoundException() {
        TicketCreateRequest ticketCreateRequest = getTicketCreateRequest();
        when(parkingLotRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verify(parkingLotRepository).findById(5);
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenParkSlotDoesNotExist_shouldThrowNotFoundException() {
        TicketCreateRequest ticketCreateRequest = getTicketCreateRequest();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotFoundException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenParkSlotIsNotAvailable_shouldThrowNotAvailableException() {
        TicketCreateRequest ticketCreateRequest = getTicketCreateRequest();
        ticketCreateRequest.setSlotID(15);
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        parkingLot.getParkSlots().get(15).setAvailable(false);

        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotAvailableException.class, () -> ticketService.createTicket(ticketCreateRequest));
        verifyNoInteractions(ticketMapper);
    }

    @Test
    public void createTicketTest_whenVehicleIsAlreadyInParkingLot_shouldThrowNotAvailableException() {
        Vehicle vehicle = getVehicle();
        TicketCreateRequest ticketCreateRequest = getTicketCreateRequest();
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
        TicketCreateRequest ticketCreateRequest = getTicketCreateRequest();
        ticketCreateRequest.setSlotID(15);
        Vehicle vehicle = getVehicle();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);
        TicketDto ticketDto = getTicketDto();
        Ticket ticket = getTicket();
        when(parkingLotRepository.findById(5)).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById(vehicle.getLicencePlate())).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findTicketByVehicleAndIsValid(vehicle, true)).thenReturn(null);
        when(ticketRepository.save(any())).thenReturn(ticket);
        when(ticketMapper.convertToDto(ticket)).thenReturn(ticketDto);

        TicketDto result = ticketService.createTicket(ticketCreateRequest);
        assertEquals(ticketDto, result);
    }

    public List<Ticket> getTicketList() {
        List<Ticket> ticketList = new ArrayList<>();

        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        for (int i = 0; i < 3; i++) {
            ticketList.add(Ticket.builder()
                    .ticketID(i)
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(true)
                    .vehicle(vehicle)
                    .parkSlot(parkingLot.getParkSlots().get(1))
                    .parkinglot(parkingLot)
                    .build());
        }
        return ticketList;
    }

    public List<TicketDto> getTicketDtoList() {
        List<TicketDto> ticketDtoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ticketDtoList.add(TicketDto.builder()
                    .ticketID(i)
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(true)
                    .licencePlate("ABC" + 1)
                    .slotID(1)
                    .lotID(1)
                    .build());
        }
        return ticketDtoList;
    }

    public TicketCreateRequest getTicketCreateRequest() {
        return TicketCreateRequest.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .lotID(5)
                .slotID(55)
                .build();
    }

    public Vehicle getVehicle() {
        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC1")
                .vehicleType(VehicleType.AUTO)
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);

        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ticketList.add(Ticket.builder()
                    .ticketID(i)
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(false)
                    .vehicle(vehicle)
                    .parkSlot(parkingLot.getParkSlots().get(i))
                    .parkinglot(parkingLot)
                    .build());
        }
        ticketList.add(Ticket.builder()
                .ticketID(3)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .vehicle(vehicle)
                .parkSlot(parkingLot.getParkSlots().get(3))
                .parkinglot(parkingLot)
                .build());

        vehicle.setTicketList(ticketList);
        return vehicle;
    }

    public TicketDto getTicketDto() {
        return TicketDto.builder()
                .ticketID(3)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .licencePlate("ABC" + 1)
                .slotID(15)
                .lotID(5)
                .build();
    }

    public Ticket getTicket() {
        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(5);

        return Ticket.builder()
                //.ticketID(3)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .vehicle(vehicle)
                .parkSlot(parkingLot.getParkSlots().get(15))
                .parkinglot(parkingLot)
                .build();
    }
}
