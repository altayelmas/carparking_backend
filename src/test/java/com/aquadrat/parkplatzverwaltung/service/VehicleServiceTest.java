package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.mapper.VehicleMapper;
import com.aquadrat.parkplatzverwaltung.model.*;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import com.aquadrat.parkplatzverwaltung.repository.VehicleRepository;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository;
    private VehicleMapper vehicleMapper;

    @BeforeEach
    public void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleMapper = mock(VehicleMapper.class);

        vehicleService = new VehicleService(vehicleRepository, vehicleMapper);
    }

    @Test
    public void getAllTest() {
        List<Vehicle> vehicleList = getVehicles();
        List<VehicleDto> vehicleDtoList = getVehicleDtos();
        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        for (int i = 0; i < 5; i++) {
            when(vehicleMapper.convertToDto(vehicleList.get(i))).thenReturn(vehicleDtoList.get(i));
        }

        List<VehicleDto> result = vehicleService.getAll();
        assertEquals(vehicleDtoList, result);
        verify(vehicleRepository).findAll();

        for (int i = 0; i < 5; i++) {
            verify(vehicleMapper).convertToDto(vehicleList.get(i));
        }
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        List<Ticket> ticketList = new ArrayList<>();

        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        for (int i = 0; i < 5; i++) {
            Vehicle vehicle = Vehicle.builder()
                    .licencePlate("ABC" + i)
                    .vehicleType(VehicleType.AUTO)
                    .build();
            ticketList = new ArrayList<>();

            ticketList.add(Ticket.builder()
                    .ticketID(ThreadLocalRandom.current().nextInt(0, 1000000))
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(false)
                    .vehicle(vehicle)
                    .parkSlot(parkingLot.getParkSlots().get(5))
                    .parkinglot(parkingLot).build());

            vehicle.setTicketList(ticketList);
            vehicle.getTicketList().add(Ticket.builder()
                    .ticketID(1000001 + i)
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(true)
                    .vehicle(vehicle)
                    .parkSlot(parkingLot.getParkSlots().get(5))
                    .parkinglot(parkingLot)
                    .build());

            vehicleList.add(vehicle);
        }

        for (int i = 6; i < 10; i++) {
            Vehicle vehicle = Vehicle.builder()
                    .licencePlate("ABC" + i)
                    .vehicleType(VehicleType.AUTO)
                    .build();
            ticketList = new ArrayList<>();

            ticketList.add(Ticket.builder()
                    .ticketID(ThreadLocalRandom.current().nextInt(0, 1000000))
                    .entryDate(new Date())
                    .exitDate(new Date())
                    .isValid(false)
                    .vehicle(vehicle)
                    .parkSlot(parkingLot.getParkSlots().get(5))
                    .parkinglot(parkingLot).build());

            vehicle.setTicketList(ticketList);
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public List<VehicleDto> getVehicleDtos() {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            vehicleDtoList.add(VehicleDto.builder()
                    .licencePlate("ABC" + i)
                    .vehicleType(VehicleType.AUTO)
                    .ticketID(1000001 + i).build());
        }
        return vehicleDtoList;
    }
}
