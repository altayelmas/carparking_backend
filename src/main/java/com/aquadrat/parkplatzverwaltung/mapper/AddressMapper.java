package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto convertToDto(Address address) {
        return AddressDto.builder()
                .addressID(address.getAddressID())
                .street(address.getStreet())
                .city(address.getCity())
                .postCode(address.getPostCode())
                .country(address.getCountry())
                .lotID(address.getParkingLot().getLotID())
                .build();
    }
}
