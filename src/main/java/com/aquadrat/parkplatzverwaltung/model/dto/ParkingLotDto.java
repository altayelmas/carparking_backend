package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ParkingLotDto {
    private Integer lotID;
    private String name;
    private AddressDto addressDto;
    private List<ParkSlotDto> parkSlotDtoList;
}
