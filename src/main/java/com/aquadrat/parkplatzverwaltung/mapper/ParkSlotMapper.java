package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkSlotDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkSlotMapper {

    public ParkSlotDto parkSlotToParkSlotDto(ParkSlot parkSlot) {
        return ParkSlotDto.builder()
                .slotID(parkSlot.getSlotID())
                .isAvailable(parkSlot.isAvailable())
                .lotID(parkSlot.getParkinglot().getLotID())
                .build();
    }

    public List<ParkSlotDto> slotListToDtoList(List<ParkSlot> parkSlotList) {
        List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();
        for (ParkSlot parkSlot: parkSlotList) {
            parkSlotDtoList.add(parkSlotToParkSlotDto(parkSlot));
        }
        return parkSlotDtoList;
    }
}
