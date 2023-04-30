package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleDto convertToDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .licencePlate(vehicle.getLicencePlate())
                .vehicleType(vehicle.getVehicleType())
                .ticketID(vehicle.getTicket().getTicketID())
                .build();
    }
}
