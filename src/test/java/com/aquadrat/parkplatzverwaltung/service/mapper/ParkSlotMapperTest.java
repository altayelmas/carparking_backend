package com.aquadrat.parkplatzverwaltung.service.mapper;

import com.aquadrat.parkplatzverwaltung.mapper.ParkSlotMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkSlotDto;
import com.aquadrat.parkplatzverwaltung.service.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkSlotMapperTest {
    private ParkSlotMapper parkSlotMapper;

    @BeforeEach
    public void setUp() {
        parkSlotMapper = new ParkSlotMapper();
    }

    @Test
    public void parkSlotToParkSlotDtoTest() {
        ParkSlot parkSlot = getParkSlot();
        ParkSlotDto parkSlotDto = getParkSlotDto();

        ParkSlotDto result = parkSlotMapper.parkSlotToParkSlotDto(parkSlot);
        assertEquals(parkSlotDto, result);
    }

    @Test
    public void slotListToDtoListTest() {
        List<ParkSlot> parkSlotList = getParkSlotList();
        List<ParkSlotDto> parkSlotDtoList = getParkSlotDtoList();

        List<ParkSlotDto> result = parkSlotMapper.slotListToDtoList(parkSlotList);
        assertEquals(parkSlotDtoList, result);
    }

    public ParkSlot getParkSlot() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        return parkingLot.getParkSlots().get(5);
    }

    public ParkSlotDto getParkSlotDto() {
        ParkSlotDto parkSlotDto = ParkSlotDto.builder()
                .slotID(5)
                .isAvailable(true)
                .lotID(1)
                .build();
        return parkSlotDto;
    }

    public List<ParkSlot> getParkSlotList() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        return parkingLot.getParkSlots();
    }

    public List<ParkSlotDto> getParkSlotDtoList() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        List<ParkSlot> parkSlotList = parkingLot.getParkSlots();
        List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();

        for (ParkSlot parkSlot : parkSlotList) {
            parkSlotDtoList.add(ParkSlotDto.builder()
                    .slotID(parkSlot.getSlotID())
                    .isAvailable(true)
                    .lotID(1)
                    .build());
        }
        return parkSlotDtoList;
    }
}
