package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingLotResponse {
    private ParkingLotDto parkingLotDto;
    private boolean isSuccess;
    private String message;
}
