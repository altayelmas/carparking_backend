package com.aquadrat.parkplatzverwaltung.model.dto;

import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketCreateRequest {
    private String licencePlate;
    private VehicleType vehicleType;
    private Integer lotID;
    private Integer slotID;
}
