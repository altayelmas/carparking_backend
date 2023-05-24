package com.aquadrat.parkplatzverwaltung.model.dto;

import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {
    private String licencePlate;
    private VehicleType vehicleType;
    private Integer lotID;
    private Integer slotID;
}
