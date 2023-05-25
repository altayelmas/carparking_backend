package com.aquadrat.parkplatzverwaltung.support;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VehicleTestSupport {

    public static Vehicle generateVehicle() {
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

    public static VehicleDto generateVehicleDto() {
        return VehicleDto.builder()
                .licencePlate("ABC1")
                .vehicleType(VehicleType.AUTO)
                .ticketID(3)
                .build();
    }

    public static List<Vehicle> generateVehicleList() {
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

    public static List<VehicleDto> generateVehicleDtoList() {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            vehicleDtoList.add(VehicleDto.builder()
                    .licencePlate("ABC" + i)
                    .vehicleType(VehicleType.AUTO)
                    .ticketID(1000001 + i).build());
        }
        return vehicleDtoList;
    }

    public static List<VehicleDto> generateVehicleDtoListWithCount(int numberOfVehicle) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        for (int i = 0; i < numberOfVehicle; i++) {

            vehicleDtoList.add(VehicleDto.builder()
                    .licencePlate("ABC1")
                    .vehicleType(VehicleType.AUTO)
                    .ticketID(3)
                    .build());
        }
        return vehicleDtoList;
    }
}
