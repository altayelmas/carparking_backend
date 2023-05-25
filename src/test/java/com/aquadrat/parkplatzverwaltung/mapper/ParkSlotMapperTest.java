package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkSlotDto;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
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
        ParkSlot parkSlot = ParkingLotTestSupport.generateParkSlot();
        ParkSlotDto parkSlotDto = ParkingLotTestSupport.generateParkSlotDto();

        ParkSlotDto result = parkSlotMapper.parkSlotToParkSlotDto(parkSlot);
        assertEquals(parkSlotDto, result);
    }

    @Test
    public void slotListToDtoListTest() {
        List<ParkSlot> parkSlotList = ParkingLotTestSupport.generateParkSlotList();
        List<ParkSlotDto> parkSlotDtoList = ParkingLotTestSupport.generateParkSlotDtoList();

        List<ParkSlotDto> result = parkSlotMapper.slotListToDtoList(parkSlotList);
        assertEquals(parkSlotDtoList, result);
    }
}
