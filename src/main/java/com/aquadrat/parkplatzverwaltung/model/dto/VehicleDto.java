package com.aquadrat.parkplatzverwaltung.model.dto;

import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleDto {
    private String licencePlate;
    private VehicleType vehicleType;
    private Integer ticketID;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        VehicleDto vehicle = (VehicleDto) obj;

        if (this.getLicencePlate().equals(vehicle.getLicencePlate()) && this.getVehicleType().equals(vehicle.getVehicleType()) && this.getTicketID() == vehicle.getTicketID()) {
            return true;
        }
        return false;
    }
}
