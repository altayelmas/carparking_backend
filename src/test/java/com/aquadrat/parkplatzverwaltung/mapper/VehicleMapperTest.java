package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleMapperTest {

    private VehicleMapper vehicleMapper;

    @BeforeEach
    public void setUp() {
        vehicleMapper = new VehicleMapper();
    }

    @Test
    public void convertToDtoTest() {
        Vehicle vehicle = getVehicle();
        VehicleDto vehicleDto = getVehicleDto();

        VehicleDto result = vehicleMapper.convertToDto(vehicle);
        assertEquals(vehicleDto, result);
    }

    public Vehicle getVehicle() {
        Vehicle vehicle = Vehicle.builder()
                .licencePlate("ABC1")
                .vehicleType(VehicleType.AUTO)
                .build();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

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

    public VehicleDto getVehicleDto() {
        VehicleDto vehicleDto = VehicleDto.builder()
                .licencePlate("ABC1")
                .vehicleType(VehicleType.AUTO)
                .ticketID(3)
                .build();

        return vehicleDto;
    }
}
