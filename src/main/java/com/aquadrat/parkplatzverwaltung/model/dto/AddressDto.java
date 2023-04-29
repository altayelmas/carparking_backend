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
}
