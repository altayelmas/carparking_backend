package com.aquadrat.parkplatzverwaltung.service.mapper;

import com.aquadrat.parkplatzverwaltung.mapper.AddressMapper;
import com.aquadrat.parkplatzverwaltung.mapper.ParkSlotMapper;
import com.aquadrat.parkplatzverwaltung.mapper.ParkingLotMapper;
import com.aquadrat.parkplatzverwaltung.mapper.TicketMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.AddressDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkSlotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.service.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        ParkingLotDto parkingLotDto = getParkingLotDto();

        when(addressMapper.convertToDto(parkingLot.getAddress())).thenReturn(parkingLotDto.getAddressDto());
        when(parkSlotMapper.slotListToDtoList(parkingLot.getParkSlots())).thenReturn(parkingLotDto.getParkSlotDtoList());

        ParkingLotDto result = parkingLotMapper.convertToDto(parkingLot);
        assertEquals(parkingLotDto, result);
        verify(addressMapper).convertToDto(parkingLot.getAddress());
        verify(parkSlotMapper).slotListToDtoList(parkingLot.getParkSlots());
    }

    @Test
    //TODO - Test failed
    public void lotListToLotDtoListTest() {
        List<ParkingLot> parkingLotList = getParkingLotList();
        List<ParkingLotDto> parkingLotDtoList = getParkingLotDtoList();

        for (int i = 0; i < parkingLotDtoList.size(); i++) {
            when(addressMapper.convertToDto(parkingLotList.get(i).getAddress())).thenReturn(parkingLotDtoList.get(i).getAddressDto());
            when(parkSlotMapper.slotListToDtoList(parkingLotList.get(i).getParkSlots())).thenReturn(parkingLotDtoList.get(i).getParkSlotDtoList());
        }

        List<ParkingLotDto> result = parkingLotMapper.lotListToLotDtoList(parkingLotList);
        assertEquals(parkingLotDtoList, result);
    }

    public ParkingLotDto getParkingLotDto() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);

        AddressDto addressDto = AddressDto.builder()
                .addressID(parkingLot.getAddress().getAddressID())
                .street(parkingLot.getAddress().getStreet())
                .city(parkingLot.getAddress().getCity())
                .postCode(parkingLot.getAddress().getPostCode())
                .country(parkingLot.getAddress().getCountry())
                .lotID(parkingLot.getLotID())
                .build();

        List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();

        for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
            parkSlotDtoList.add(ParkSlotDto.builder()
                    .slotID(parkSlot.getSlotID())
                    .isAvailable(true)
                    .lotID(1)
                    .build());
        }

        return ParkingLotDto.builder()
                .lotID(parkingLot.getLotID())
                .name(parkingLot.getName())
                .addressDto(addressDto)
                .parkSlotDtoList(parkSlotDtoList)
                .build();
    }

    public List<ParkingLot> getParkingLotList() {
        List<ParkingLot> parkingLotList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            parkingLotList.add(ParkingLotTestSupport.generateParkingLot(i));
        }
        return parkingLotList;
    }

    public List<ParkingLotDto> getParkingLotDtoList() {
        List<ParkingLotDto> parkingLotDtoList = new ArrayList<>();
        List<ParkingLot> parkingLotList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            parkingLotList.add(ParkingLotTestSupport.generateParkingLot(i));
        }

        for (int i = 0; i < 3; i++) {

            AddressDto addressDto = AddressDto.builder()
                    .addressID(parkingLotList.get(i).getAddress().getAddressID())
                    .street(parkingLotList.get(i).getAddress().getStreet())
                    .city(parkingLotList.get(i).getAddress().getCity())
                    .postCode(parkingLotList.get(i).getAddress().getPostCode())
                    .country(parkingLotList.get(i).getAddress().getCountry())
                    .lotID(1)
                    .build();

            List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();
            for (ParkSlot parkSlot : parkingLotList.get(i).getParkSlots()) {
                parkSlotDtoList.add(ParkSlotDto.builder()
                        .slotID(parkSlot.getSlotID())
                        .isAvailable(true)
                        .lotID(i)
                        .build());
            }

            parkingLotDtoList.add(ParkingLotDto.builder()
                    .lotID(parkingLotList.get(i).getLotID())
                    .name(parkingLotList.get(i).getName())
                    .addressDto(addressDto)
                    .parkSlotDtoList(parkSlotDtoList)
                    .build());
        }
        return parkingLotDtoList;
    }
}
