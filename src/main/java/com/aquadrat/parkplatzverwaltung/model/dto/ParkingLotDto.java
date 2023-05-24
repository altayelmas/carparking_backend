package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ParkingLotDto {
    private Integer lotID;
    private String name;
    private AddressDto addressDto;
    private List<ParkSlotDto> parkSlotDtoList;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ParkingLotDto parkingLotDto = (ParkingLotDto) obj;

        if (this.lotID == parkingLotDto.getLotID() && this.name.equals(parkingLotDto.getName()) && this.addressDto.equals(parkingLotDto.getAddressDto())) {
            return true;
        }
        return false;
    }
}
