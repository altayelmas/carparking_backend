package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.AddressDto;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = new AddressMapper();
    }

    @Test
    public void convertToDtoTest() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        Address address = parkingLot.getAddress();
        AddressDto addressDto = getAddressDto();

        AddressDto result = addressMapper.convertToDto(address);
        assertEquals(addressDto, result);
    }

    private AddressDto getAddressDto() {
        AddressDto addressDto = AddressDto.builder()
                .addressID(1)
                .street("TestStreet")
                .city("TestCity")
                .postCode("TestPostCode")
                .country("TestCountry")
                .lotID(1)
                .build();

        return addressDto;
    }


}
