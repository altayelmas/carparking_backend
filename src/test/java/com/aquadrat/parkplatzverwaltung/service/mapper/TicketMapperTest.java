package com.aquadrat.parkplatzverwaltung.service.mapper;

import com.aquadrat.parkplatzverwaltung.mapper.TicketMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import com.aquadrat.parkplatzverwaltung.service.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketMapperTest {

    private TicketMapper ticketMapper;

    @BeforeEach
    public void setUp() {
        ticketMapper = new TicketMapper();
    }

    @Test
    public void convertToDtoTest() {
        Ticket ticket = getTicket();
        TicketDto ticketDto = getTicketDto();

        TicketDto result = ticketMapper.convertToDto(ticket);
        assertEquals(ticketDto, result);
    }

    @Test
    public void ticketListToTicketDtoListTest() {
        List<Ticket> ticketList = getTicketList();
        List<TicketDto> ticketDtoList = getTicketDtoList();

        List<TicketDto> result = ticketMapper.ticketListToTicketDtoList(ticketList);
        assertEquals(ticketDtoList, result);
    }

    public Ticket getTicket() {
        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        return Ticket.builder()
                .ticketID(1)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .vehicle(vehicle)
                .parkSlot(parkingLot.getParkSlots().get(1))
                .parkinglot(parkingLot)
                .build();
    }

    public TicketDto getTicketDto() {
        return TicketDto.builder()
                .ticketID(1)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .licencePlate("ABC" + 1)
                .slotID(1)
                .lotID(1)
                .build();
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
}
