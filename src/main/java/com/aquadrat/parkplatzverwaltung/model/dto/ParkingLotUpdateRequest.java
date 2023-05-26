package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotUpdateRequest {
    private String name;
    private String street;
    private String city;
    private String postCode;
    private String country;
    private Integer numberOfSlots;
}
