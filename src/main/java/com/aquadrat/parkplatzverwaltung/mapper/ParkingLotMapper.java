package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingLotMapper {
    private final AddressMapper addressMapper;
    private final ParkSlotMapper parkSlotMapper;
    private final TicketMapper ticketMapper;

    public ParkingLotMapper(AddressMapper addressMapper, ParkSlotMapper parkSlotMapper, TicketMapper ticketMapper) {
        this.addressMapper = addressMapper;
        this.parkSlotMapper = parkSlotMapper;
        this.ticketMapper = ticketMapper;
    }

    public ParkingLotDto convertToDto(ParkingLot parkingLot) {
        return ParkingLotDto.builder()
                .lotID(parkingLot.getLotID())
                .name(parkingLot.getName())
                .addressDto(addressMapper.convertToDto(parkingLot.getAddress()))
                .parkSlotDtoList(parkSlotMapper.slotListToDtoList(parkingLot.getParkSlots()))
                .build();

    }

    public List<ParkingLotDto> lotListToLotDtoList(List<ParkingLot> parkingLotList) {
        List<ParkingLotDto> parkingLotDtoList = new ArrayList<>();
        for (ParkingLot parkingLot: parkingLotList) {
            parkingLotDtoList.add(convertToDto(parkingLot));
        }
        return parkingLotDtoList;
    }
}
