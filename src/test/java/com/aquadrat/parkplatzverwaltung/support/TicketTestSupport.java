package com.aquadrat.parkplatzverwaltung.support;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketResponse;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketTestSupport {

    public static Ticket generateTicket(int ticketID) {
        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .ticketList(new ArrayList<>())
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        Ticket ticket = Ticket.builder()
                .ticketID(ticketID)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .vehicle(vehicle)
                .parkSlot(parkingLot.getParkSlots().get(1))
                .parkinglot(parkingLot)
                .build();

        vehicle.getTicketList().add(ticket);
        return ticket;
    }

    public static TicketDto generateTicketDto(int ticketID) {
        return TicketDto.builder()
                .ticketID(ticketID)
                .entryDate(new Date())
                .exitDate(new Date())
                .isValid(true)
                .licencePlate("ABC" + 1)
                .slotID(1)
                .lotID(1)
                .build();
    }

    public static List<Ticket> generateTicketList(int count) {
        List<Ticket> ticketList = new ArrayList<>();

        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .ticketList(new ArrayList<>())
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        for (int i = 0; i < count; i++) {
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

    public static List<TicketDto> generateTicketDtoList(int count) {
        List<TicketDto> ticketDtoList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
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

    public static TicketCreateRequest generateTicketCreateRequest() {
        return TicketCreateRequest.builder()
                .licencePlate("ABC" + 1)
                .vehicleType(VehicleType.AUTO)
                .lotID(5)
                .slotID(55)
                .build();
    }
    public static TicketResponse generateTicketResponse(TicketDto ticketDto, boolean isSuccess, String message) {
        return TicketResponse.builder()
                .ticketDto(ticketDto)
                .isSuccess(isSuccess)
                .message(message)
                .build();
    }
}
