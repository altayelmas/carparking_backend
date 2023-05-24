package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {
    private Integer addressID;
    private String street;
    private String city;
    private String postCode;
    private String country;
    private Integer lotID;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        AddressDto addressDto = (AddressDto) obj;

        if (this.addressID == addressDto.getAddressID() && this.street.equals(addressDto.getStreet()) && this.postCode.equals(addressDto.getPostCode()) && this.country.equals(addressDto.getCountry()) && this.lotID == addressDto.getLotID()) {
            return true;
        }
        return false;
    }
}
