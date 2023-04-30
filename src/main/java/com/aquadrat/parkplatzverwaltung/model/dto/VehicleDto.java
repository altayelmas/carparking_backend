package com.aquadrat.parkplatzverwaltung.model.dto;

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
}
