package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotResponse {
    private ParkingLotDto parkingLotDto;
    private boolean isSuccess;
    private String message;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        ParkingLotResponse parkingLotResponse = (ParkingLotResponse) obj;
        if ((this.parkingLotDto == null && parkingLotResponse.getParkingLotDto() == null) && this.isSuccess == parkingLotResponse.isSuccess() && this.message.equals(parkingLotResponse.getMessage())) {
            return true;
        }

        if (this.parkingLotDto.equals(parkingLotResponse.getParkingLotDto()) && this.isSuccess == parkingLotResponse.isSuccess() && this.message.equals(parkingLotResponse.getMessage())) {
            return true;
        }
        return false;
    }
}
