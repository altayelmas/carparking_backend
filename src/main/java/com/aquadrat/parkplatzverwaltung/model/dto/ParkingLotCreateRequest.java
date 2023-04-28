package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingLotCreateRequest {
    private String name;
    private String street;
    private String city;
    private String postCode;
    private String country;
    private Integer numberOfSlots;
}
