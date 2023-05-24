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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ParkSlotDto parkSlotDto = (ParkSlotDto) obj;

        if (this.slotID == parkSlotDto.getSlotID() && this.isAvailable() == parkSlotDto.isAvailable() && this.lotID == parkSlotDto.getLotID()) {
            return true;
        }
        return false;
    }
}
