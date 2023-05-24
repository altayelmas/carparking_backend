package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.mapper.ParkingLotMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.AddressDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkSlotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingLotServiceTest {

    private ParkingLotService parkingLotService;
    private ParkingLotRepository parkingLotRepository;
    private ParkingLotMapper parkingLotMapper;
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setUp() {
        parkingLotRepository = mock(ParkingLotRepository.class);
        parkingLotMapper = mock(ParkingLotMapper.class);
        ticketRepository = mock(TicketRepository.class);

        parkingLotService = new ParkingLotService(parkingLotRepository, parkingLotMapper, ticketRepository);
    }

    @Test
    public void createParkingLotTest() {
        ParkingLotCreateRequest parkingLotCreateRequest = parkingLotCreateRequest();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotDto parkingLotDto = getParkingLotDto();
        when(parkingLotMapper.convertToDto(parkingLot)).thenReturn(parkingLotDto);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        ParkingLotDto result = parkingLotService.createParkingLot(parkingLotCreateRequest);
        assertEquals(parkingLotDto, result);
        verify(parkingLotMapper).convertToDto(parkingLot);
    }

    public ParkingLotCreateRequest parkingLotCreateRequest() {
        return ParkingLotCreateRequest.builder()
                .name("TestParkingLot")
                .street("TestStreet")
                .city("TestCity")
                .postCode("TestPostCode")
                .country("TestCountry")
                .numberOfSlots(50)
                .build();
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


}
