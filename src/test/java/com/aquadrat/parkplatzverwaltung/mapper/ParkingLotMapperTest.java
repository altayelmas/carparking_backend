package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingLotMapperTest {
    private ParkingLotMapper parkingLotMapper;
    private AddressMapper addressMapper;
    private ParkSlotMapper parkSlotMapper;
    private TicketMapper ticketMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = mock(AddressMapper.class);
        parkSlotMapper = mock(ParkSlotMapper.class);
        ticketMapper = mock(TicketMapper.class);

        parkingLotMapper = new ParkingLotMapper(addressMapper, parkSlotMapper, ticketMapper);
    }

    @Test
    public void convertToDtoTest() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);

        when(addressMapper.convertToDto(parkingLot.getAddress())).thenReturn(parkingLotDto.getAddressDto());
        when(parkSlotMapper.slotListToDtoList(parkingLot.getParkSlots())).thenReturn(parkingLotDto.getParkSlotDtoList());

        ParkingLotDto result = parkingLotMapper.convertToDto(parkingLot);
        assertEquals(parkingLotDto, result);
        verify(addressMapper).convertToDto(parkingLot.getAddress());
        verify(parkSlotMapper).slotListToDtoList(parkingLot.getParkSlots());
    }

    @Test
    public void lotListToLotDtoListTest() {
        List<ParkingLot> parkingLotList = ParkingLotTestSupport.generateParkingLotList(3);
        List<ParkingLotDto> parkingLotDtoList = ParkingLotTestSupport.generateParkingLotDtoList(3);

        for (int i = 0; i < parkingLotDtoList.size(); i++) {
            when(addressMapper.convertToDto(parkingLotList.get(i).getAddress())).thenReturn(parkingLotDtoList.get(i).getAddressDto());
            when(parkSlotMapper.slotListToDtoList(parkingLotList.get(i).getParkSlots())).thenReturn(parkingLotDtoList.get(i).getParkSlotDtoList());
        }

        List<ParkingLotDto> result = parkingLotMapper.lotListToLotDtoList(parkingLotList);
        assertEquals(parkingLotDtoList, result);
    }
}
