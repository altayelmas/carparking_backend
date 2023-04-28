package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class TicketDto {

    private Integer ticketID;
    private Date entryDate;
    private Date exitDate;
    private String licencePlate;
    private Integer slotID;
    private Integer lotID;
}
