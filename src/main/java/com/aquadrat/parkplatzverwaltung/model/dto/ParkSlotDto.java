package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParkSlotDto {
    private Integer slotID;
    private boolean isAvailable;
    private Integer lotID;
}
